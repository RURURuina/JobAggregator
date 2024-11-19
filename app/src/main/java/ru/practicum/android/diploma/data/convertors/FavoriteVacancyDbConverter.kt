package ru.practicum.android.diploma.data.convertors

import ru.practicum.android.diploma.data.db.FavoritesVacancyEntity
import ru.practicum.android.diploma.domain.models.entity.Address
import ru.practicum.android.diploma.domain.models.entity.Area
import ru.practicum.android.diploma.domain.models.entity.Contacts
import ru.practicum.android.diploma.domain.models.entity.Country
import ru.practicum.android.diploma.domain.models.entity.DriverLicense
import ru.practicum.android.diploma.domain.models.entity.Employer
import ru.practicum.android.diploma.domain.models.entity.EmployerData
import ru.practicum.android.diploma.domain.models.entity.Employment
import ru.practicum.android.diploma.domain.models.entity.Experience
import ru.practicum.android.diploma.domain.models.entity.Industry
import ru.practicum.android.diploma.domain.models.entity.KeySkill
import ru.practicum.android.diploma.domain.models.entity.Language
import ru.practicum.android.diploma.domain.models.entity.LogoUrlsData
import ru.practicum.android.diploma.domain.models.entity.Salary
import ru.practicum.android.diploma.domain.models.entity.SalaryData
import ru.practicum.android.diploma.domain.models.entity.Schedule
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.domain.models.entity.VacancyShort

class FavoriteVacancyDbConverter {

    fun vacancyToEntity(vacancy: Vacancy): FavoritesVacancyEntity = FavoritesVacancyEntity(
        id = vacancy.id,
        name = vacancy.name,
        employer = vacancy.employer?.name,
        salary = vacancy.salary?.run { "$currency $from-$to" },
        address = vacancy.adress?.full,
        experience = vacancy.experience?.name,
        employment = vacancy.employment?.name,
        keySkills = vacancy.keySkills?.joinToString { it.name ?:"" },
        languages = vacancy.languages?.joinToString { it.name ?: "" },
        driverLicenseTypes = vacancy.driverLicenseTypes?.joinToString { it.id ?: "" },
        area = vacancy.area?.name,
        industry = vacancy.industry?.name,
        country = vacancy.country?.name,
        contacts = vacancy.contacts?.run { "$name: ${phones?.joinToString { it.formatted }}" },
        description = vacancy.description,
        schedule = vacancy.schedule?.name,
        url = vacancy.url
//        employerName = this.employer?.name,
//        logoOriginal = this.employer?.logoUrls?.original,
//        currency = this.salaryData?.currency,
//        from = this.salaryData?.from,
//        to = this.salaryData?.to
    )

    fun vacancyEntityToDomain(vacancyEntity: FavoritesVacancyEntity): Vacancy = Vacancy(
        id = vacancyEntity.id,
        name = vacancyEntity.name,
        employer = Employer(
            name = vacancyEntity.employer,
            logoUrls = null
            ),
        salary = vacancyEntity.salary?.split("")?.let {
            Salary(
                currency = it[0],
                from = it[1].split("-")[0].toInt(),
                to = it[1].split("-")[1].toInt(),
                gross = null
            )
        },
        adress = Address(
            full = vacancyEntity.address,
            building = null,
            city = null,
            description = null,
            metro = null,
            street = null
        ),
        experience = Experience(name = vacancyEntity.experience),
        employment = Employment(name = vacancyEntity.employment),
        keySkills = vacancyEntity.keySkills?.split(",")?.map{ KeySkill(name = it) },
        languages = vacancyEntity.languages?.split(", ")?.map { Language(name = it, level = null) },
        driverLicenseTypes = vacancyEntity.driverLicenseTypes?.split(", ")?.map { DriverLicense(id = it)},
        area = Area(id = null, name = vacancyEntity.area, url = null),
        industry = Industry
            (id = "",
            name = vacancyEntity.industry ?: "",
            industries = null),
        country = Country(id = "", name = vacancyEntity.country ?: ""),
        contacts = Contacts(email = null, name = vacancyEntity.contacts, phones = null),
        description = vacancyEntity.description,
        schedule = Schedule(id = null, name = vacancyEntity.schedule),
        url = vacancyEntity.url
    )
}
