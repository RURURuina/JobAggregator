package ru.practicum.android.diploma.presentation.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.hh.HhInteractor
import ru.practicum.android.diploma.domain.models.entity.Country
import ru.practicum.android.diploma.ui.country.model.CountrySelectState
import java.net.SocketTimeoutException

class SelectCountryViewModel(private val hhInteractor: HhInteractor) : ViewModel() {
    private val _countrySelectState = MutableLiveData<CountrySelectState>()
    val countrySelectState: LiveData<CountrySelectState> = _countrySelectState
    private var countriesList: MutableList<Country> = mutableListOf()

    init {
        getCountries()
    }

    fun chooseCountry() = { country: Country ->
        saveToFilter(country)
        renderState(CountrySelectState.Exit)
    }

    private fun saveToFilter(country: Country) {
        // Реализация сохранения страны в фильтр
    }

    private fun getCountries() {
        viewModelScope.launch {
            try {
                hhInteractor.searchCountries().collect { resource ->
                    resource?.data?.let { listCountries ->
                        countriesList.addAll(listCountries)
                        renderState(CountrySelectState.Success(listCountries))
                    } ?: renderState(CountrySelectState.Error)
                }
            } catch (e: SocketTimeoutException) {
                this.coroutineContext.job.cancel()
            }
        }
    }

    private fun renderState(state: CountrySelectState) {
        _countrySelectState.postValue(state)
    }
}
