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
            viewModelScope.launch {
                filterInteractor.saveFilter(filterShared)
            }
        }

    fun loadSavedFilter() {
        viewModelScope.launch {
            filterShared = filterInteractor.getFilter()
        }
    }

    fun changeSalary(salary: String?) {
       val total= if (salary.isNullOrEmpty()){
            null
        }else{
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
    }

    fun saveFilter() {
        filterShared = filterShared
    }

    fun resetFilter() {
        filterShared = null
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
    }

}
