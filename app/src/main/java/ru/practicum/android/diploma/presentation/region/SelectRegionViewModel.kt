package ru.practicum.android.diploma.presentation.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.filter.FilterInteractor
import ru.practicum.android.diploma.domain.models.entity.FilterShared
import ru.practicum.android.diploma.ui.region.model.SelectRegionFragmentState

class SelectRegionViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    private val _state = MutableLiveData<SelectRegionFragmentState>()
    val state: LiveData<SelectRegionFragmentState> = _state
    private var filterShared: FilterShared? = null
        set(value) {
            pushState(
                SelectRegionFragmentState.Content(
                    value?.countryName,
                    value?.regionName,
                    value?.countryId
                )
            )
            field = value
        }

    fun getFilter() {
        viewModelScope.launch {
            filterShared = filterInteractor.getFilter()
        }
    }

    fun clearCountry() {
        filterShared = filterShared?.copy(
            countryName = null,
            countryId = null,
            apply = null
        )
    }

    fun clearArea() {
        filterShared = filterShared?.copy(
            regionId = null,
            regionName = null,
            apply = null
        )
    }

    fun saveExit() {
        viewModelScope.launch {
            filterInteractor.saveFilter(filterShared?.copy(apply = true))
            pushState(SelectRegionFragmentState.Exit)
        }
    }

    private fun pushState(state: SelectRegionFragmentState) {
        _state.postValue(state)
    }
}
