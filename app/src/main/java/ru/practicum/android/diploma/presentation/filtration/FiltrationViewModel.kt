package ru.practicum.android.diploma.presentation.filtration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.filter.FilterInteractor
import ru.practicum.android.diploma.domain.models.entity.FilterShared

class FiltrationViewModel(private val filterInteractor: FilterInteractor) : ViewModel() {
    private val _filterState = MutableLiveData<FilterShared?>()
    val filterState: LiveData<FilterShared?> = _filterState
    private var filterShared: FilterShared? = null
        set(value) {
            _filterState.value = value
            field = value
        }

    fun loadSavedFilter() {
        viewModelScope.launch {
            filterShared = filterInteractor.getFilter()
        }
    }

    fun changeSalary(salary: String?) {
        filterShared?.let { oldFilter ->
            filterShared = oldFilter.copy(salary = salary)
        } ?: {
            filterShared = FilterShared(
                null,
                countryId = null,
                regionName = null,
                regionId = null,
                industryName = null, industryId = null,
                salary = salary,
                onlySalaryFlag = null,
                apply = null
            )
        }
    }

    fun checkingOnlySalaryFlag(onlySalaryFlag: Boolean) {
        filterShared?.let { oldFilter ->
            filterShared = oldFilter.copy(onlySalaryFlag = onlySalaryFlag)
        } ?: {
            filterShared = FilterShared(
                null,
                countryId = null,
                regionName = null,
                regionId = null,
                industryName = null,
                industryId = null,
                salary = "0",
                onlySalaryFlag = onlySalaryFlag,
                apply = null
            )
        }
    }

    fun saveFilter() {
        viewModelScope.launch {
            filterInteractor.saveFilter(filterShared?.copy(apply = true))
        }
    }

    fun resetFilter() {
        filterShared = null
        viewModelScope.launch {
            filterInteractor.saveFilter(filterShared)
        }
    }

    fun resetWorkPlace() {
        filterShared?.let { oldFilter ->
            filterShared = oldFilter.copy(
                countryId = null,
                countryName = null,
                regionId = null,
                regionName = null
            )
        }
    }

    fun resetIndustry() {
        filterShared?.let { oldFilter ->
            filterShared = oldFilter.copy(
                industryId = null,
                industryName = null
            )
        }
    }
}
