package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.convertors.FavoriteVacancyDbConverter
import ru.practicum.android.diploma.data.db.FavoritesVacancyDao
import ru.practicum.android.diploma.data.db.FavoritesVacancyEntity
import ru.practicum.android.diploma.domain.api.FavoritesVacancyRepository
import ru.practicum.android.diploma.domain.models.entity.Vacancy

class FavoritesVacancyRepositoryImpl(
    private val favoritesVacancyDao: FavoritesVacancyDao,
    private val favoriteVacancyDbConverter: FavoriteVacancyDbConverter,
) : FavoritesVacancyRepository {
    override suspend fun insertVacancy(vacancy: Vacancy) {
        val vacancyEntity = favoriteVacancyDbConverter.vacancyToEntity(vacancy)
        favoritesVacancyDao.insertVacancy(vacancyEntity)
    }

    override suspend fun deleteVacancy(vacancy: Vacancy) {
        val vacancyEntity = favoriteVacancyDbConverter.vacancyToEntity(vacancy)
        favoritesVacancyDao.deleteVacancy(vacancyEntity)
    }

    override suspend fun isFavoriteCheck(vacancyId: String): Boolean {
        return favoritesVacancyDao.getAllFavoritesIds().contains(vacancyId)
    }

    override fun getFavoriteVacancies(): Flow<List<Vacancy>> {
        return favoritesVacancyDao.getFavoriteVacancies().map {
            convertEntityToDomain(it)
        }

    }

    override suspend fun getFavoriteVacancyById(id: String): Vacancy {
        val vacancyEntity = favoritesVacancyDao.getFavoriteVacancyById(id)
        return favoriteVacancyDbConverter.vacancyEntityToDomain(vacancyEntity)

    }

    private fun convertEntityToDomain(vacancies: List<FavoritesVacancyEntity>): List<Vacancy> {
        return vacancies.map { vacancy -> favoriteVacancyDbConverter.vacancyEntityToDomain(vacancy) }
    }
}

