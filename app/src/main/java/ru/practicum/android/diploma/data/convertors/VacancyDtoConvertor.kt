package ru.practicum.android.diploma.data.convertors

import ru.practicum.android.diploma.data.dto.vacancy.DriverLicenseData
import ru.practicum.android.diploma.data.dto.vacancy.EmployerData
import ru.practicum.android.diploma.data.dto.vacancy.EmploymentData
import ru.practicum.android.diploma.data.dto.vacancy.ExperienceData
import ru.practicum.android.diploma.data.dto.vacancy.KeySkillData
import ru.practicum.android.diploma.data.dto.vacancy.LanguageData
import ru.practicum.android.diploma.data.dto.vacancy.LanguageLevelData
import ru.practicum.android.diploma.data.dto.vacancy.LogoUrlsData
import ru.practicum.android.diploma.data.dto.vacancy.SalaryData
import ru.practicum.android.diploma.data.dto.vacancy.VacancyData
import ru.practicum.android.diploma.domain.models.entity.DriverLicense
import ru.practicum.android.diploma.domain.models.entity.Employer
import ru.practicum.android.diploma.domain.models.entity.Employment
import ru.practicum.android.diploma.domain.models.entity.Experience
import ru.practicum.android.diploma.domain.models.entity.KeySkill
import ru.practicum.android.diploma.domain.models.entity.Language
import ru.practicum.android.diploma.domain.models.entity.LanguageLevel
import ru.practicum.android.diploma.domain.models.entity.LogoUrls
import ru.practicum.android.diploma.domain.models.entity.Salary
import ru.practicum.android.diploma.domain.models.entity.Vacancy

class VacancyDtoConvertor {
    // класс яростного мапинга классов из слоя дата для домейн
    // могут возникнуть вопросы к мапперам работающим с лист
    fun map(vacancy: VacancyData): Vacancy {
        println(vacancy.employer)
        return Vacancy(
            vacancy.id,
            vacancy.name,
            vacancy.employer.map(),
            vacancy.salaryData.map(),
            vacancy.experience.map(),
            vacancy.employment.map(),
            keySkillsListMap(vacancy.keySkills),
            languageList(vacancy.languages),
            driverLicenseListMap(vacancy.driverLicenseTypes),
        )
    }

    private fun EmployerData?.map(): Employer? {
        this?.let {
            return Employer(
                this.name,
                this.logoUrls.map()
            )
        } ?: return null
    }

    private fun LogoUrlsData?.map(): LogoUrls? {
        this?.let {
            return LogoUrls(
                this.small,
                this.medium,
                this.original
            )
        } ?: return null
    }

    private fun SalaryData?.map(): Salary? {
        this?.let {
            return Salary(
                this.currency,
                this.from,
                this.gross,
                this.to
            )
        } ?: return null
    }

    private fun ExperienceData?.map(): Experience? {
        this?.let {
            return Experience(
                this.name,
            )
        } ?: return null
    }

    private fun EmploymentData?.map(): Employment? {
        this?.let {
            return Employment(
                this.name,
            )
        } ?: return null
    }

    private fun keySkillsListMap(oldList: List<KeySkillData>?): List<KeySkill>? {
        oldList?.let {
            val list = mutableListOf<KeySkill>()
            oldList.map { list.add(it.map()) }
            return list
        } ?: return null
    }

    private fun KeySkillData.map(): KeySkill {
        return KeySkill(
            this.name,
        )
    }

    private fun LanguageData.map(): Language {
        return Language(
            this.name,
            this.level.map()
        )
    }

    private fun LanguageLevelData?.map(): LanguageLevel? {
        this?.let {
            return LanguageLevel(
                this.name
            )
        } ?: return null
    }

    private fun languageList(oldList: List<LanguageData>?): List<Language>? {
        oldList?.let {
            val list = mutableListOf<Language>()
            oldList.map { list.add(it.map()) }
            return list
        } ?: return null
    }

    private fun driverLicenseListMap(olList: List<DriverLicenseData>?): List<DriverLicense>? {
        olList?.let {
            val list = mutableListOf<DriverLicense>()
            olList.map { list.add(it.map()) }
            return list
        } ?: return null
    }

    private fun DriverLicenseData.map(): DriverLicense {
        return DriverLicense(
            this.id
        )
    }
}
