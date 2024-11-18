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
    id = this.id,
    name = this.name,
    employerName = this.employer?.name,
    logoOriginal = this.employer?.logoUrls?.original,
    currency = this.salaryData?.currency,
    from = this.salaryData?.from,
    to = this.salaryData?.to
)

fun FavoritesVacancyEntity.toDomain(): VacancyShort = VacancyShort(
    id = this.id,
    name = this.name,
    employer = EmployerData(
        name = this.employerName,
        LogoUrlsData(
            original = this.logoOriginal
        )
    ),
    salaryData = SalaryData(
        currency = this.currency,
        from = this.from,
        to = this.to
    )
)
