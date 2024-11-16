package ru.practicum.android.diploma.ui.favorites.models

import ru.practicum.android.diploma.domain.models.entity.Vacancy

sealed interface FavoritesState {
    data object Loading: FavoritesState

    data class Content(
        val favoritesVacancies: List<Vacancy>
    ): FavoritesState

    data class EmptyOrError(
       val message: Int
    ): FavoritesState

}
