package ru.practicum.android.diploma.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.hh.HhInteractor
import ru.practicum.android.diploma.domain.api.sharing.VacancySharingInteractor
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.ui.details.models.DetailsFragmentState
import ru.practicum.android.diploma.util.Resource

class DetailsFragmentViewModel(
    private val hhInteractor: HhInteractor,
    private val vacancySharingInteractor: VacancySharingInteractor,
) : ViewModel() {
    private var vacancy: Vacancy? = null
    private val stateLiveData = MutableLiveData<DetailsFragmentState>()

    fun observeState(): LiveData<DetailsFragmentState> = stateLiveData

    private fun renderState(state: DetailsFragmentState) {
        stateLiveData.postValue(state)
    }

    fun start(id: String) {
        viewModelScope.launch {
            val a = hhInteractor.searchVacanceById(id)
            a.collect { resource: Resource<Vacancy> ->
                resource.data?.let {
                    vacancy = it
                    renderState(DetailsFragmentState.Content(it))
                } ?: renderState(DetailsFragmentState.ERROR(resource.responseCode!!))
            }
        }
    }

    fun shareVacancy() {
        viewModelScope.launch {
            vacancy?.let {
                vacancySharingInteractor.shareVacancy(it)
            }
        }
    }

    fun likeButton(){
        println("надо дождаться функционала сохранения в базу")
    }
}
