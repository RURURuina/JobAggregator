package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.domain.models.entity.VacancyShort

interface FavoritesVacancyRepository {
    suspend fun insertVacancy(vacancy: Vacancy)
    suspend fun deleteVacancy(vacancy: Vacancy)
    fun getFavoriteVacancies(): Flow<List<Vacancy>>
    fun getFavoriteVacancyById(id: String): Vacancy
}
