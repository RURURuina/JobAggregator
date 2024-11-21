package ru.practicum.android.diploma.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.hh.HhInteractor
import ru.practicum.android.diploma.domain.api.sharing.VacancySharingInteractor
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.ui.details.models.DetailsFragmentState
import ru.practicum.android.diploma.util.Resource

class DetailsFragmentViewModel(
    private val hhInteractor: HhInteractor,
    private val favoritesInteractor: FavoritesInteractor,
    private val vacancySharingInteractor: VacancySharingInteractor,
) : ViewModel() {
    private var vacancy: Vacancy? = null
    private val stateLiveData = MutableLiveData<DetailsFragmentState>()

    fun observeState(): LiveData<DetailsFragmentState> = stateLiveData

    //    liveData для иконки добавления в избранное
    private var _isFavoriteLiveData = MutableLiveData<Boolean>()
    val isFavoriteLiveData: LiveData<Boolean> = _isFavoriteLiveData

    private fun renderState(state: DetailsFragmentState) {
        stateLiveData.postValue(state)
    }

    // изменил на изначальную загрузку с бд
    fun start(id: String) {
        viewModelScope.launch {
            val isCached = favoritesInteractor.isFavoriteCheck(id)
            if (isCached) {
                val cachedVacancy = favoritesInteractor.getFavoriteVacancyById(id)
                cachedVacancy?.let {
                    vacancy = it
                    renderState(DetailsFragmentState.Content(it))
                    _isFavoriteLiveData.postValue(vacancy?.isFavorite)
                }
            } else {
                hhInteractor.searchVacanceById(id).collect { resource: Resource<Vacancy?> ->
                    resource.data?.let {
                        vacancy = it
                        renderState(DetailsFragmentState.Content(it))
                    } ?: renderState(DetailsFragmentState.ERROR(resource.responseCode))
                }
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

    fun likeButton() {
        val currentVacancy = vacancy ?: return
        viewModelScope.launch {
            if (favoritesInteractor.isFavoriteCheck(currentVacancy.id)) {
                favoritesInteractor.deleteVacancy(currentVacancy)
                currentVacancy.isFavorite = false
                _isFavoriteLiveData.postValue(false)
            } else {
                favoritesInteractor.insertVacancy(currentVacancy)
                currentVacancy.isFavorite = true
                _isFavoriteLiveData.postValue(true)
            }
        }
    }
}
