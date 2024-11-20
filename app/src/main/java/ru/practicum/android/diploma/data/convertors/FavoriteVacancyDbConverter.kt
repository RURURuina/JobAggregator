package ru.practicum.android.diploma.data.convertors

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


    fun vacancyEntityToDomain(vacancyEntity: FavoritesVacancyEntity): Vacancy {
        /*в процессе доработки*/
        fun <T> safeFromJson(json: String?, typeOfT: TypeToken<T>): T? {
            return try {
                gson.fromJson(json, typeOfT.type)
            } catch (e: JsonSyntaxException) {
                null
            }
        }
        return Vacancy(
            id = vacancyEntity.id,
            name = vacancyEntity.name,
            employer = safeFromJson(vacancyEntity.employer, object : TypeToken<Employer>() {}) ?: Employer(null, null),
            salary = safeFromJson(vacancyEntity.salary, object : TypeToken<Salary>() {}) ?: Salary(
                null,
                null,
                null,
                null
            ),
            adress = safeFromJson(vacancyEntity.address, object : TypeToken<Address>() {}) ?: Address(
                null,
                null,
                null,
                null,
                null,
                null
            ),
            experience = safeFromJson(vacancyEntity.experience, object : TypeToken<Experience>() {})
                ?: Experience(null),
            employment = safeFromJson(vacancyEntity.employment, object : TypeToken<Employment>() {})
                ?: Employment(null),
            keySkills = safeFromJson(vacancyEntity.keySkills, object : TypeToken<List<KeySkill>>() {}) ?: emptyList(),
            languages = safeFromJson(vacancyEntity.languages, object : TypeToken<List<Language>>() {}) ?: emptyList(),
            driverLicenseTypes = safeFromJson(
                vacancyEntity.driverLicenseTypes,
                object : TypeToken<List<DriverLicense>>() {}) ?: emptyList(),
            area = safeFromJson(vacancyEntity.area, object : TypeToken<Area>() {}) ?: Area(null, null, null),
            industry = safeFromJson(vacancyEntity.industry, object : TypeToken<Industry>() {}) ?: Industry(
                "",
                "",
                null
            ),
            country = safeFromJson(vacancyEntity.country, object : TypeToken<Country>() {}) ?: Country("", ""),
            contacts = safeFromJson(vacancyEntity.contacts, object : TypeToken<Contacts>() {}) ?: Contacts(
                null,
                null,
                null
            ),
            description = vacancyEntity.description,
            schedule = safeFromJson(vacancyEntity.schedule, object : TypeToken<Schedule>() {}) ?: Schedule(null, null),
            url = vacancyEntity.url,
            isFavorite = true
        )
    }
}
