package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.FavoritesVacancyRepository
import ru.practicum.android.diploma.domain.models.entity.VacancyShort

class FavoritesInteractorImpl(val repository: FavoritesVacancyRepository) : FavoritesInteractor {
    override suspend fun insertVacancy(vacancy: VacancyShort) {
        repository.insertVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancy: VacancyShort) {
        repository.deleteVacancy(vacancy)
    }

    override fun getFavoriteVacancies(): Flow<List<VacancyShort>> {
        return repository.getFavoriteVacancies()
    }

    override fun getFavoriteVacancyById(id: String): Flow<VacancyShort> {
        return repository.getFavoriteVacancyById(id)
    }
}
