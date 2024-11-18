package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.FavoritesVacancyEntity
import ru.practicum.android.diploma.domain.api.FavoritesVacancyRepository
import ru.practicum.android.diploma.domain.models.entity.EmployerData
import ru.practicum.android.diploma.domain.models.entity.LogoUrlsData
import ru.practicum.android.diploma.domain.models.entity.SalaryData
import ru.practicum.android.diploma.domain.models.entity.VacancyShort

class FavoritesVacancyRepositoryImpl(val appDatabase: AppDatabase) : FavoritesVacancyRepository {
    override suspend fun insertVacancy(vacancy: VacancyShort) {
        appDatabase.favoritesVacancyDao().insertVacancy(vacancy.toData())
    }

    override suspend fun deleteVacancy(vacancy: VacancyShort) {
        appDatabase.favoritesVacancyDao().deleteVacancy(vacancy.toData())
    }

    override fun getFavoriteVacancies(): Flow<List<VacancyShort>> = flow {
        emit(appDatabase.favoritesVacancyDao().getFavoriteVacancies().map { it.toDomain() })
    }

    override fun getFavoriteVacancyById(id: String): Flow<VacancyShort> = flow {
        emit(appDatabase.favoritesVacancyDao().getFavoriteVacancyById(id).toDomain())
    }
}

fun VacancyShort.toData(): FavoritesVacancyEntity = FavoritesVacancyEntity(
    this.id,
    this.name,
    this.employer?.name,
    this.employer?.logoUrls?.original,
    this.salaryData?.currency,
    this.salaryData?.from,
    this.salaryData?.to
)

fun FavoritesVacancyEntity.toDomain(): VacancyShort = VacancyShort(
    this.id, this.name, EmployerData(
        this.employerName, LogoUrlsData(
            this.logoOriginal
        )
    ), SalaryData(
        this.currency, this.from, this.to
    )
)
