package ru.practicum.android.diploma.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.HhInteractor
import ru.practicum.android.diploma.ui.details.models.DetailsFragmentState

class DetailsFragmentViewModel(private val hhInteractor: HhInteractor) : ViewModel() {
    init {
        println("DetailsFragmentViewModel")
    }

    private val stateLiveData = MutableLiveData<DetailsFragmentState>()

    fun observeState(): LiveData<DetailsFragmentState> = stateLiveData

    private fun renderState(state: DetailsFragmentState) {
        stateLiveData.postValue(state)
    }

    fun start(id: String) {
        viewModelScope.launch {
            val a = hhInteractor.searchVacanceById(id)
            a.collect { println(it.data) }
        }
    }
}
