package ru.practicum.android.diploma.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.ui.favorites.models.FavoritesState

class FavoriteJobViewModel : ViewModel() {
    init {
        // тут пока пусто
    }

    // liveData для отрисовки экрана на будущее
    private val _favoritesState = MutableLiveData<FavoritesState>()
    val favoritesState: LiveData<FavoritesState> = _favoritesState



    //функция для обновления liveData
    private fun pushFavoriteState(state: FavoritesState){
        _favoritesState.postValue(state)
    }
}
