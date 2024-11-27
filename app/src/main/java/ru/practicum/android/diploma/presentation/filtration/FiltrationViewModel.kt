package ru.practicum.android.diploma.presentation.filtration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.filter.FilterInteractor
import ru.practicum.android.diploma.domain.models.entity.FilterShared
import ru.practicum.android.diploma.domain.models.entity.Salary

class FiltrationViewModel(private val filterInteractor: FilterInteractor) : ViewModel() {
    private val _filterState = MutableLiveData<FilterShared?>()
    val filterState : LiveData<FilterShared?> = _filterState

    fun loadSavedFilter() {
        viewModelScope.launch {
            _filterState.value = filterInteractor.getFilter()
        }
    }

    fun saveFilter(filterShared: FilterShared) {
        viewModelScope.launch {
            filterInteractor.saveFilter(filterShared)
            _filterState.value = filterShared
        }
    }

    fun createFilterFromUI(salary: String?, onlySalaryFlag: Boolean?) : FilterShared {
        val currentFilter = _filterState.value ?: FilterShared(
            countryName = null,
            countryId = null,
            regionName = null,
            regionId = null,
            industryName = null,
            industryId = null,
            salary = null,
            onlySalaryFlag = null
        )
        return currentFilter.copy(
            salary = salary,
            onlySalaryFlag = onlySalaryFlag
        )
    }
}
