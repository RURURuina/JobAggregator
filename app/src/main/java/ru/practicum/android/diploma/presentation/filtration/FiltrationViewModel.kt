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
        filterShared = FilterShared(
            countryName = filterShared?.countryName,
            countryId = filterShared?.countryId,
            regionName = filterShared?.regionName,
            regionId = filterShared?.regionId,
            industryName = filterShared?.industryName,
            industryId = filterShared?.countryId,
            salary = salary,
            onlySalaryFlag = filterShared?.onlySalaryFlag,
            apply = filterShared?.apply
        )
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
            apply = filterShared?.apply
        )
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
        filterShared = FilterShared(
            countryId = null,
            countryName = null,
            regionId = null,
            regionName = null,
            industryName = filterShared?.industryName,
            industryId = filterShared?.countryId,
            salary = filterShared?.salary,
            onlySalaryFlag = filterShared?.onlySalaryFlag,
            apply = filterShared?.apply
        )
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
            apply = filterShared?.apply
        )
    }

}
