package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.entity.Vacancy

interface FavoritesVacancyRepository {
    suspend fun insertVacancy(vacancy: Vacancy)
    suspend fun deleteVacancy(vacancy: Vacancy)
    suspend fun isFavoriteCheck(vacancyId: String): Boolean
    fun getFavoriteVacancies(): Flow<List<Vacancy>>
    suspend fun getFavoriteVacancyById(id: String): Vacancy?
}
