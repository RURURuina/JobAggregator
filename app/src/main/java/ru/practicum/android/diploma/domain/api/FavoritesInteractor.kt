package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.entity.VacancyShort

interface FavoritesInteractor {
    suspend fun insertVacancy(vacancy: VacancyShort)
    suspend fun deleteVacancy(vacancy: VacancyShort)
    fun getFavoriteVacancies(): Flow<List<VacancyShort>>
    fun getFavoriteVacancyById(id: String): Flow<VacancyShort>
}
