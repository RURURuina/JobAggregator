package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.FavoritesVacancyRepository
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.domain.models.entity.VacancyShort

class FavoritesInteractorImpl(val repository: FavoritesVacancyRepository) : FavoritesInteractor {
    override suspend fun insertVacancy(vacancy: Vacancy) {
        repository.insertVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancy: Vacancy) {
        repository.deleteVacancy(vacancy)
    }

    override fun getFavoriteVacancies(): Flow<List<Vacancy>> {
        return repository.getFavoriteVacancies()
    }

    override fun getFavoriteVacancyById(id: String): Vacancy {
        return repository.getFavoriteVacancyById(id)
    }
}
