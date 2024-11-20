package ru.practicum.android.diploma.data.convertors

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.FavoritesVacancyEntity
import ru.practicum.android.diploma.domain.models.entity.Address
import ru.practicum.android.diploma.domain.models.entity.Area
import ru.practicum.android.diploma.domain.models.entity.Contacts
import ru.practicum.android.diploma.domain.models.entity.Country
import ru.practicum.android.diploma.domain.models.entity.DriverLicense
import ru.practicum.android.diploma.domain.models.entity.Employer
import ru.practicum.android.diploma.domain.models.entity.Employment
import ru.practicum.android.diploma.domain.models.entity.Experience
import ru.practicum.android.diploma.domain.models.entity.Industry
import ru.practicum.android.diploma.domain.models.entity.KeySkill
import ru.practicum.android.diploma.domain.models.entity.Language
import ru.practicum.android.diploma.domain.models.entity.Salary
import ru.practicum.android.diploma.domain.models.entity.Schedule
import ru.practicum.android.diploma.domain.models.entity.Vacancy

class FavoriteVacancyDbConverter(
    private val gson: Gson,
) {
    fun vacancyToEntity(vacancy: Vacancy): FavoritesVacancyEntity = FavoritesVacancyEntity(
        id = vacancy.id,
        name = vacancy.name,
        employer = gson.toJson(vacancy.employer),
        salary = gson.toJson(vacancy.salary),
        address = gson.toJson(vacancy.adress),
        experience = gson.toJson(vacancy.experience),
        employment = gson.toJson(vacancy.employment),
        keySkills = gson.toJson(vacancy.keySkills),
        languages = gson.toJson(vacancy.languages),
        driverLicenseTypes = gson.toJson(vacancy.driverLicenseTypes),
        area = gson.toJson(vacancy.area),
        industry = gson.toJson(vacancy.industry),
        country = gson.toJson(vacancy.country),
        contacts = gson.toJson(vacancy.contacts),
        description = vacancy.description,
        schedule = gson.toJson(vacancy.schedule),
        url = vacancy.url
    )

    fun vacancyEntityToDomain(vacancyEntity: FavoritesVacancyEntity?): Vacancy? {
        return vacancyEntity?.let {
            Vacancy(
                id = vacancyEntity.id,
                name = vacancyEntity.name,
                employer = getEmployer(vacancyEntity),
                salary = getSalary(vacancyEntity),
                adress = getAddress(vacancyEntity),
                experience = getExperience(vacancyEntity),
                employment = getEmployment(vacancyEntity),
                keySkills = getKeySkills(vacancyEntity),
                languages = getLanguages(vacancyEntity),
                driverLicenseTypes = getDriverLicenseTypes(vacancyEntity),
                area = getArea(vacancyEntity),
                industry = getIndustry(vacancyEntity),
                country = getCountry(vacancyEntity),
                contacts = getContacts(vacancyEntity),
                description = vacancyEntity.description,
                schedule = getSchedule(vacancyEntity),
                url = vacancyEntity.url,
                isFavorite = true
            )
        }
    }

    private fun getEmployer(vacancyEntity: FavoritesVacancyEntity): Employer {
        return safeFromJson(vacancyEntity.employer, object : TypeToken<Employer>() {}) ?: Employer(
            null,
            null
        )
    }


    private fun getSalary(vacancyEntity: FavoritesVacancyEntity): Salary {
        return safeFromJson(vacancyEntity.salary, object : TypeToken<Salary>() {}) ?: Salary(
            null,
            null,
            null,
            null
        )
    }

    private fun getAddress(vacancyEntity: FavoritesVacancyEntity): Address {
        return safeFromJson(vacancyEntity.address, object : TypeToken<Address>() {}) ?: Address(
            null,
            null,
            null,
            null,
            null,
            null
        )
    }

    private fun getExperience(vacancyEntity: FavoritesVacancyEntity): Experience {
        return safeFromJson(vacancyEntity.experience, object : TypeToken<Experience>() {})
            ?: Experience(null)
    }

    private fun getEmployment(vacancyEntity: FavoritesVacancyEntity): Employment {
        return safeFromJson(vacancyEntity.employment, object : TypeToken<Employment>() {})
            ?: Employment(null)
    }

    private fun getKeySkills(vacancyEntity: FavoritesVacancyEntity): List<KeySkill> {
        return safeFromJson(vacancyEntity.keySkills, object : TypeToken<List<KeySkill>>() {})
            ?: emptyList()
    }

    private fun getLanguages(vacancyEntity: FavoritesVacancyEntity): List<Language> {
        return safeFromJson(vacancyEntity.languages, object : TypeToken<List<Language>>() {})
            ?: emptyList()
    }

    private fun getDriverLicenseTypes(vacancyEntity: FavoritesVacancyEntity): List<DriverLicense> {
        return safeFromJson(
            vacancyEntity.driverLicenseTypes,
            object : TypeToken<List<DriverLicense>>() {}) ?: emptyList()
    }

    private fun getArea(vacancyEntity: FavoritesVacancyEntity): Area {
        return safeFromJson(vacancyEntity.area, object : TypeToken<Area>() {}) ?: Area(
            null,
            null,
            null
        )
    }

    private fun getIndustry(vacancyEntity: FavoritesVacancyEntity): Industry {
        return safeFromJson(vacancyEntity.industry, object : TypeToken<Industry>() {}) ?: Industry(
            "",
            "",
            null
        )
    }

    private fun getCountry(vacancyEntity: FavoritesVacancyEntity): Country {
        return safeFromJson(vacancyEntity.country, object : TypeToken<Country>() {}) ?: Country(
            "",
            ""
        )
    }

    private fun getContacts(vacancyEntity: FavoritesVacancyEntity): Contacts {
        return safeFromJson(vacancyEntity.contacts, object : TypeToken<Contacts>() {}) ?: Contacts(
            null,
            null,
            null
        )
    }

    private fun getSchedule(vacancyEntity: FavoritesVacancyEntity): Schedule {
        return safeFromJson(vacancyEntity.schedule, object : TypeToken<Schedule>() {}) ?: Schedule(
            null,
            null
        )
    }

    private fun <T> safeFromJson(json: String?, typeOfT: TypeToken<T>): T? {
        return try {
            gson.fromJson(json, typeOfT.type)
        } catch (e: JsonSyntaxException) {
            Log.e("FavoriteVacancyDbConverter", "JsonSyntaxException, $e")
            null
        }
    }
}
