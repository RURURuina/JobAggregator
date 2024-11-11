package ru.practicum.android.diploma.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.HhInteractor

class SearchJobViewModel(private val hhInteractor: HhInteractor) : ViewModel() {

    fun start() {
        viewModelScope.launch {
            hhInteractor.getVacancies(hashMapOf()).collect {
                it.data?.map { Log.d("SearchJobViewModel", "vacancy: $it") }
            }
        }
    }
}

