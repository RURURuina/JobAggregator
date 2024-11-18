package ru.practicum.android.diploma.ui.favorites.models

import ru.practicum.android.diploma.domain.models.entity.Vacancy

sealed interface FavoritesState {
    data object Loading : FavoritesState

    data class Content(
        val favoritesVacancies: List<Vacancy>
    ) : FavoritesState

    data class Empty(
        val message: Int
    ) : FavoritesState

    data class Error(
        val message: Int
    ) : FavoritesState

}
