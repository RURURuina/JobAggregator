package ru.practicum.android.diploma.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.ui.favorites.models.FavoritesState

class FavoriteJobViewModel : ViewModel() {

    // нужно добавить интерактор
    // liveData для отрисовки экрана на будущее
    private val _favoritesState = MutableLiveData<FavoritesState>()
    val favoritesState: LiveData<FavoritesState> = _favoritesState

    fun getVacancies() {
        viewModelScope.launch {
            /*
            Блок для получения вакансий Например:
            interactor.getVacancies()
                .onStart { pushFavoriteState(FavoriteState.Loading)}
                .catch {exception->
                    pushFavoriteState(FavoriteState.Error(R.string.couldnt_get_job_list))    //обработка ошибок
                .collect { vacancies->
                    showData(vacancies)
             */
        }
    }

    private fun showData(vacancies: List<Vacancy>) {
        if (vacancies.isEmpty()) {
            pushFavoriteState(FavoritesState.Empty(R.string.empty_list))
        } else {
            pushFavoriteState(FavoritesState.Content(vacancies))
        }
    }

    // функция для обновления liveData
    private fun pushFavoriteState(state: FavoritesState) {
        _favoritesState.postValue(state)
    }
}
