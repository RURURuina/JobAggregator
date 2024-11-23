package ru.practicum.android.diploma.presentation.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.entity.Industry
import ru.practicum.android.diploma.domain.models.entity.IndustryNested
import ru.practicum.android.diploma.util.Resource

class IndustryViewModel(val interactor: IndustriesInteractor) : ViewModel() {
    private var unFilteredList : List<IndustryNested> = emptyList()

     private val _industries = MutableLiveData<List<IndustryNested>>()
     var industries: LiveData<List<IndustryNested>> = _industries

    private val _selectedIndustry = MutableLiveData<IndustryNested>()
    val selectedIndustry: LiveData<IndustryNested> = _selectedIndustry

    init {
        getIndustries()
    }

    private fun getIndustries() {
        viewModelScope.launch {
            interactor.getIndustriesList().collect { result ->
                when(result) {
                    is Resource.Success -> {
                      unFilteredList = result.data?.flatMap { industry ->
                          industry.industries!!
                      }!!
                        _industries.value = unFilteredList
                    }

                    is Resource.Error -> {
                        //обработка ошибки
                    }
                }
            }
        }
    }
}
