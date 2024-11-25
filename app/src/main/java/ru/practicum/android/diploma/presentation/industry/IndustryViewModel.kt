package ru.practicum.android.diploma.presentation.industry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.industries.IndustriesInteractor

class IndustryViewModel(val industriesInteractor: IndustriesInteractor) : ViewModel() {
    init {
        viewModelScope.launch {
            industriesInteractor.getIndustriesList().collect{result->
               val a= result.data?.let {

               }
            }
        }
    }
}
