package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.convertors.FavoriteVacancyDbConverter
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.FavoritesVacancyDao
import ru.practicum.android.diploma.data.db.FavoritesVacancyEntity
import ru.practicum.android.diploma.domain.api.FavoritesVacancyRepository
import ru.practicum.android.diploma.domain.models.entity.EmployerData
import ru.practicum.android.diploma.domain.models.entity.LogoUrlsData
import ru.practicum.android.diploma.domain.models.entity.SalaryData
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.domain.models.entity.VacancyShort

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

    override fun getFavoriteVacancies(): Flow<List<Vacancy>> {
      return favoritesVacancyDao.getFavoriteVacancies().map {
            convertEntityToDomain(it)
        }
    }

    override fun getFavoriteVacancyById(id: String): Vacancy {
        val vacancyEntity = favoritesVacancyDao.getFavoriteVacancyById(id)
        return favoriteVacancyDbConverter.vacancyEntityToDomain(vacancyEntity)

    }

    private fun convertEntityToDomain(vacancies: List<FavoritesVacancyEntity>): List<Vacancy> {
        return vacancies.map { vacancy -> favoriteVacancyDbConverter.vacancyEntityToDomain(vacancy) }
    }
}
//fun VacancyShort.toData(): FavoritesVacancyEntity = FavoritesVacancyEntity(
//    id = this.id,
//    name = this.name,
//    employerName = this.employer?.name,
//    logoOriginal = this.employer?.logoUrls?.original,
//    currency = this.salaryData?.currency,
//    from = this.salaryData?.from,
//    to = this.salaryData?.to
//)
//
//fun FavoritesVacancyEntity.toDomain(): VacancyShort = VacancyShort(
//    id = this.id,
//    name = this.name,
//    employer = EmployerData(
//        name = this.employerName,
//        LogoUrlsData(
//            original = this.logoOriginal
//        )
//    ),
//    salaryData = SalaryData(
//        currency = this.currency,
//        from = this.from,
//        to = this.to
//    )
//)
