package ru.practicum.android.diploma.presentation.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.filter.FilterInteractor
import ru.practicum.android.diploma.domain.api.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.entity.FilterShared
import ru.practicum.android.diploma.domain.models.entity.IndustryNested
import ru.practicum.android.diploma.ui.industry.models.IndustryFragmentState
import ru.practicum.android.diploma.util.Resource

class IndustryViewModel(
    private val interactor: IndustriesInteractor,
    private val filterInteractor: FilterInteractor
) : ViewModel() {
    private var unFilteredList: List<IndustryNested> = emptyList()
    private var filterJob: Job? = null
    private var filterShared: FilterShared? = null
    private val _industriesState = MutableLiveData<IndustryFragmentState>()
    val industries: LiveData<IndustryFragmentState> = _industriesState
    private var selectedIndustry: IndustryNested? = null

    init {
        getFilter()
        setItem()
        getIndustries()
    }

    fun saveFilter() {
        viewModelScope.launch {
                filterInteractor.saveFilter(
                FilterShared(
                    countryId = filterShared?.countryId,
                    countryName = filterShared?.countryName,
                    regionId = filterShared?.regionId,
                    regionName = filterShared?.regionName,
                    industryName = selectedIndustry?.name,
                    industryId = selectedIndustry?.id,
                    salary = null,
                    onlySalaryFlag = false
                )
            )
        }
        pushState(IndustryFragmentState.Exit)
    }


private fun setItem() {
//    filterShared?.let { filterShared ->
//        _selectedIndustry.value = IndustryNested(
//            id = filterShared.industryId,
//            name = filterShared.industryName
//        )
//    }
}

private fun getFilter() {
    viewModelScope.launch {
        filterShared = filterInteractor.getFilter()
    }
}

private fun getIndustries() {
    viewModelScope.launch {
        interactor.getIndustriesList().collect { result ->
            when (result) {
                is Resource.Success -> {
                    unFilteredList = result.data?.flatMap { industry ->
                        industry.industries ?: emptyList()
                    } ?: emptyList()

                    pushState(IndustryFragmentState.Content(unFilteredList))
                }

                is Resource.Error -> {
                    // Handle error state
                }
            }
        }
    }
}

fun setSelectedIndustry(industry: IndustryNested) {
    selectedIndustry = industry
}

fun filterIndustries(query: String) {
    filterJob?.cancel() // Отменяем предыдущую операцию фильтрации
    filterJob = viewModelScope.launch {
        val filteredList = if (query.isEmpty()) {
            unFilteredList
        } else {
            unFilteredList.filter { industry ->
                industry.name?.contains(query, ignoreCase = true) == true
            }
        }
        pushState(IndustryFragmentState.Content(filteredList))
    }
}

private fun pushState(state: IndustryFragmentState) {
    _industriesState.value = state
}
}
