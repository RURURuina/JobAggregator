package ru.practicum.android.diploma.presentation.country

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.filter.FilterInteractor
import ru.practicum.android.diploma.domain.api.hh.HhInteractor
import ru.practicum.android.diploma.domain.models.entity.Country
import ru.practicum.android.diploma.domain.models.entity.FilterShared
import ru.practicum.android.diploma.ui.country.model.CountrySelectState
import java.net.SocketTimeoutException

class SelectCountryViewModel(
    private val hhInteractor: HhInteractor,
    private val filterInteractor: FilterInteractor
) :
    ViewModel() {
    private val _countrySelectState = MutableLiveData<CountrySelectState>()
    val countrySelectState: LiveData<CountrySelectState> = _countrySelectState
    private var countriesList: MutableList<Country> = mutableListOf()
    private var filterShared: FilterShared? = null

    init {
        getCountries()
    }

    fun chooseCountry() = { country: Country ->
        if (country.id == "other") {
            renderState(CountrySelectState.Success(countriesList))
        }else{
            saveToFilter(country)
            renderState(CountrySelectState.Exit)
        }
    }

    private fun saveToFilter(country: Country) {
        viewModelScope.launch {
            filterShared?.let { filterShared ->
                filterInteractor.saveFilter(
                    filterShared.copy(
                        countryId = country.id,
                        countryName = country.name,
                    )
                )
            } ?: filterInteractor.saveFilter(
                FilterShared(
                    countryId = country.id,
                    countryName = country.name,
                    regionId = null,
                    regionName = null,
                    industryName = null,
                    industryId = null,
                    salary = null,
                    onlySalaryFlag = false,
                    apply = null
                )
            )
            renderState(CountrySelectState.Exit)
        }
    }

    private fun getCountries() {
        viewModelScope.launch {
            try {
                hhInteractor.searchCountries().collect { resource ->
                    resource.data?.let { listCountries ->
                        countriesList.addAll(listCountries)
                        val fixedCountries = listOf(
                            listCountries.find { it.name == "Россия" },
                            listCountries.find { it.name == "Украина" },
                            listCountries.find { it.name == "Казахстан" },
                            listCountries.find { it.name == "Азербайджан" },
                            listCountries.find { it.name == "Беларусь" },
                            listCountries.find { it.name == "Грузия" },
                            listCountries.find { it.name == "Кыргызстан" },
                            listCountries.find { it.name == "Узбекистан" }
                        ).filterNotNull()
                        val otherCountry = Country(
                            id = "other",
                            name = "Другие регионы"
                        )
                        renderState(CountrySelectState.Success(fixedCountries + otherCountry))
                    } ?: renderState(CountrySelectState.Error)
                }
            } catch (e: SocketTimeoutException) {
                Log.e("SocketTimeoutException", "Timeout error occured", e)
                renderState(CountrySelectState.Error)
                this.coroutineContext.job.cancel()
            }
        }
    }

    private fun renderState(state: CountrySelectState) {
        _countrySelectState.postValue(state)
    }
}
