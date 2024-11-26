package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.favorite.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.favorite.FavoritesVacancyRepository
import ru.practicum.android.diploma.domain.models.entity.Vacancy

class FavoritesInteractorImpl(val repository: FavoritesVacancyRepository) : FavoritesInteractor {
    override suspend fun insertVacancy(vacancy: Vacancy) {
        repository.insertVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancy: Vacancy) {
        repository.deleteVacancy(vacancy)
    }

    override suspend fun isFavoriteCheck(vacancyId: String): Boolean {
        return repository.isFavoriteCheck(vacancyId)
    }

    override fun getFavoriteVacancies(): Flow<List<Vacancy>> {
        return repository.getFavoriteVacancies()
    }

    override suspend fun getFavoriteVacancyById(id: String): Vacancy? {
        return repository.getFavoriteVacancyById(id)
    }
}
