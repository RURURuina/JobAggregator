package ru.practicum.android.diploma.presentation.filtration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.filter.FilterInteractor
import ru.practicum.android.diploma.domain.models.entity.FilterShared
import ru.practicum.android.diploma.util.debounce

class FiltrationViewModel(private val filterInteractor: FilterInteractor) : ViewModel() {
    private val _filterState = MutableLiveData<FilterShared?>()
    val filterState: LiveData<FilterShared?> = _filterState
    private var filterShared: FilterShared? = null
    val salaryDebounce = debounce<String?>(
        DEBOUNCE_SALARY_INPUT,
        viewModelScope,
        true
    ) { string ->
        changeSalary(string)
    }

    fun loadSavedFilter() {
        viewModelScope.launch {
            filterShared = filterInteractor.getFilter()
            _filterState.value = filterShared
        }
    }

    private fun changeSalary(salary: String?) {
        val total = if (salary.isNullOrEmpty()) {
            null
        } else {
            salary
        }
        filterShared = FilterShared(
            countryName = filterShared?.countryName,
            countryId = filterShared?.countryId,
            regionName = filterShared?.regionName,
            regionId = filterShared?.regionId,
            industryName = filterShared?.industryName,
            industryId = filterShared?.countryId,
            salary = total,
            onlySalaryFlag = filterShared?.onlySalaryFlag,
        )
        _filterState.value = filterShared
    }

    fun checkingOnlySalaryFlag(onlySalaryFlag: Boolean) {
        filterShared = FilterShared(
            countryName = filterShared?.countryName,
            countryId = filterShared?.countryId,
            regionName = filterShared?.regionName,
            regionId = filterShared?.regionId,
            industryName = filterShared?.industryName,
            industryId = filterShared?.countryId,
            salary = filterShared?.salary,
            onlySalaryFlag = onlySalaryFlag,
        )
        _filterState.value = filterShared
    }

    fun saveFilter() {
        viewModelScope.launch {
            filterInteractor.saveFilter(filterShared)
        }
    }

    fun resetFilter() {
        filterShared = null
        viewModelScope.launch {
            filterInteractor.saveFilter(filterShared)
        }
    }

    fun resetWorkPlace() {
        filterShared = FilterShared(
            countryId = null,
            countryName = null,
            regionId = null,
            regionName = null,
            industryName = filterShared?.industryName,
            industryId = filterShared?.countryId,
            salary = filterShared?.salary,
            onlySalaryFlag = filterShared?.onlySalaryFlag,
        )
        viewModelScope.launch {
            filterInteractor.saveFilter(filterShared)
        }
        _filterState.value = filterShared
    }

    fun resetIndustry() {
        filterShared = FilterShared(
            countryName = filterShared?.countryName,
            countryId = filterShared?.countryId,
            regionName = filterShared?.regionName,
            regionId = filterShared?.regionId,
            industryId = null,
            industryName = null,
            salary = filterShared?.salary,
            onlySalaryFlag = filterShared?.onlySalaryFlag,
        )
        viewModelScope.launch {
            filterInteractor.saveFilter(filterShared)
        }
        _filterState.value = filterShared
    }

    private companion object {
        const val DEBOUNCE_SALARY_INPUT = 600L
    }
}
