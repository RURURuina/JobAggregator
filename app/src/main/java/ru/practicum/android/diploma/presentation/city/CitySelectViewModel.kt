package ru.practicum.android.diploma.presentation.city

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.city.CitySelectInteractor
import ru.practicum.android.diploma.domain.api.filter.FilterInteractor
import ru.practicum.android.diploma.domain.models.entity.Area
import ru.practicum.android.diploma.domain.models.entity.FilterShared
import ru.practicum.android.diploma.ui.city.model.CitySelectState
import ru.practicum.android.diploma.util.debounce
import java.net.SocketTimeoutException

class CitySelectViewModel(
    private val citySelectInteractor: CitySelectInteractor,
    private val filterInteractor: FilterInteractor
) : ViewModel() {
    private val _citySelectState = MutableLiveData<CitySelectState>()
    val citySelectState: LiveData<CitySelectState> = _citySelectState
    private var areasList: MutableList<Area> = mutableListOf()
    private var filterShared: FilterShared? = null
    val debounceFilter = debounce<String>(
        delayMillis = DEBOUNCE_FILTER_TIME,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { filterText ->
        filterRegions(filterText)
    }

    init {
        viewModelScope.launch {
            filterShared = filterInteractor.getFilter()
            filterShared?.countryId?.let {
                getCitiesById(it)
            } ?: getAllAreas()
        }
    }

    fun chooseArea() = { area: Area ->
        saveToFilter(area)
    }

    private fun saveToFilter(area: Area) {
        // что то, что сохранит в фильтр данные
        viewModelScope.launch {
            filterInteractor.saveFilter(
                FilterShared(
                    countryId = area.parentId,
                    countryName = area.parentName,
                    regionId = area.id,
                    regionName = area.name,
                    industryName = filterShared?.industryName,
                    industryId = filterShared?.industryId,
                    salary = filterShared?.salary,
                    onlySalaryFlag = filterShared?.onlySalaryFlag
                )
            )
            pushState(CitySelectState.Exit)
        }
    }

    private fun getCitiesById(id: String) {
        viewModelScope.launch {
            try {
                citySelectInteractor.getCitiesByAreaId(id).collect { resource ->
                    resource?.data?.let { listAreas ->
                        if (listAreas.isEmpty()) {
                            pushState(CitySelectState.Error)
                        } else {
                            areasList.addAll(listAreas)
                            pushState(CitySelectState.Success(listAreas))
                        }
                    } ?: pushState(CitySelectState.Error)
                }
            } catch (e: SocketTimeoutException) {
                this.coroutineContext.job.cancel()
                handleErrorSocketTimeoutException(e)

            }
        }
    }

    private fun getAllAreas() {
        viewModelScope.launch {
            try {
                citySelectInteractor.getAllArea().collect { resource ->
                    resource?.data?.let { listAreas ->
                        if (listAreas.isEmpty()) {
                            pushState(CitySelectState.Empty)
                        } else {
                            areasList.addAll(listAreas)
                            pushState(CitySelectState.Success(listAreas))
                        }
                    } ?: pushState(CitySelectState.Error)
                }
            } catch (e: SocketTimeoutException) {
                this.coroutineContext.job.cancel()
                handleErrorSocketTimeoutException(e)

            }
        }
    }

    private fun pushState(state: CitySelectState) {
        _citySelectState.postValue(state)
    }

    private fun handleErrorSocketTimeoutException(e: SocketTimeoutException) {
        handleError()
        Log.e("SearchViewModel", "SocketTimeoutException, $e")
    }

    private fun handleError() {
        pushState(CitySelectState.Error)
    }

    private fun filterRegions(text: String) {
        val filteredRegions = areasList.filter { area: Area ->
            area.name?.lowercase()?.contains(text.lowercase()) == true
        }
        if (filteredRegions.isEmpty()) {
            pushState(
                CitySelectState.Empty
            )
        } else {
            pushState(
                CitySelectState.Success(filteredRegions)
            )
        }
    }

    private companion object {
        private const val DEBOUNCE_FILTER_TIME = 2000L
    }
}
