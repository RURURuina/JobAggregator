package ru.practicum.android.diploma.data.convertors

import ru.practicum.android.diploma.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.data.dto.vacancy.DriverLicenseData
import ru.practicum.android.diploma.data.dto.vacancy.KeySkillData
import ru.practicum.android.diploma.data.dto.vacancy.LanguageData
import ru.practicum.android.diploma.data.dto.vacancy.VacancyData
import ru.practicum.android.diploma.data.dto.vacancy.map
import ru.practicum.android.diploma.domain.models.entity.DriverLicense
import ru.practicum.android.diploma.domain.models.entity.KeySkill
import ru.practicum.android.diploma.domain.models.entity.Language
import ru.practicum.android.diploma.domain.models.entity.Vacancy

class VacancyDtoConvertor {
    // класс яростного мапинга классов из слоя дата для домейн
    // могут возникнуть вопросы к мапперам работающим с лист
    fun map(vacancy: VacancyData): Vacancy {
        return Vacancy(
            vacancy.id,
            vacancy.name,
            vacancy.employer?.map(),
            vacancy.salaryData?.map(),
            vacancy.adress?.map(),
            vacancy.experience?.map(),
            vacancy.employment?.map(),
            vacancy.keySkills.mapToListKeySkill(),
            vacancy.languages.mapToLostLanguage(),
            vacancy.driverLicenseTypes.mapToListDriverLicense(),
            vacancy.area?.map(),
            vacancy.industry?.map(),
            vacancy.country?.map(),
            vacancy.contacts?.map(),
            vacancy.description,
            vacancy.schedule?.map(),
            vacancy.url
        )

    }

    fun map(vacancy: VacancyResponse?): Vacancy? {
        return map(vacancy)
    }

    private fun List<KeySkillData>?.mapToListKeySkill(): List<KeySkill>? {
        this?.let { oldList: List<KeySkillData> ->
            val list = mutableListOf<KeySkill>()
            oldList.map { keySkillData: KeySkillData -> list.add(keySkillData.map()) }
            return list
        } ?: return null
    }

    private fun List<LanguageData>?.mapToLostLanguage(): List<Language>? {
        this?.let { oldList: List<LanguageData> ->
            val list = mutableListOf<Language>()
            oldList.map { languageData: LanguageData -> list.add(languageData.map()) }
            return list
        } ?: return null
    }

    private fun List<DriverLicenseData>?.mapToListDriverLicense(): List<DriverLicense>? {
        this?.let { oldList: List<DriverLicenseData> ->
            val list = mutableListOf<DriverLicense>()
            oldList.map { driverLicenseData: DriverLicenseData -> list.add(driverLicenseData.map()) }
            return list
        } ?: return null
    }
}
