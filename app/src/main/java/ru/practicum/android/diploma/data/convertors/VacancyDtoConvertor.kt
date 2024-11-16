package ru.practicum.android.diploma.data.convertors

import ru.practicum.android.diploma.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.data.dto.vacancy.AddressData
import ru.practicum.android.diploma.data.dto.vacancy.AreaData
import ru.practicum.android.diploma.data.dto.vacancy.ContactsData
import ru.practicum.android.diploma.data.dto.vacancy.CountryData
import ru.practicum.android.diploma.data.dto.vacancy.DriverLicenseData
import ru.practicum.android.diploma.data.dto.vacancy.EmployerData
import ru.practicum.android.diploma.data.dto.vacancy.EmploymentData
import ru.practicum.android.diploma.data.dto.vacancy.ExperienceData
import ru.practicum.android.diploma.data.dto.vacancy.IndustryData
import ru.practicum.android.diploma.data.dto.vacancy.IndustryNestedData
import ru.practicum.android.diploma.data.dto.vacancy.KeySkillData
import ru.practicum.android.diploma.data.dto.vacancy.LanguageData
import ru.practicum.android.diploma.data.dto.vacancy.LanguageLevelData
import ru.practicum.android.diploma.data.dto.vacancy.LogoUrlsData
import ru.practicum.android.diploma.data.dto.vacancy.MetroStationsData
import ru.practicum.android.diploma.data.dto.vacancy.PhonesData
import ru.practicum.android.diploma.data.dto.vacancy.SalaryData
import ru.practicum.android.diploma.data.dto.vacancy.VacancyData
import ru.practicum.android.diploma.domain.models.entity.Address
import ru.practicum.android.diploma.domain.models.entity.Area
import ru.practicum.android.diploma.domain.models.entity.Contacts
import ru.practicum.android.diploma.domain.models.entity.Country
import ru.practicum.android.diploma.domain.models.entity.DriverLicense
import ru.practicum.android.diploma.domain.models.entity.Employer
import ru.practicum.android.diploma.domain.models.entity.Employment
import ru.practicum.android.diploma.domain.models.entity.Experience
import ru.practicum.android.diploma.domain.models.entity.Industry
import ru.practicum.android.diploma.domain.models.entity.IndustryNested
import ru.practicum.android.diploma.domain.models.entity.KeySkill
import ru.practicum.android.diploma.domain.models.entity.Language
import ru.practicum.android.diploma.domain.models.entity.LanguageLevel
import ru.practicum.android.diploma.domain.models.entity.LogoUrls
import ru.practicum.android.diploma.domain.models.entity.MetroStations
import ru.practicum.android.diploma.domain.models.entity.Phone
import ru.practicum.android.diploma.domain.models.entity.Salary
import ru.practicum.android.diploma.domain.models.entity.Vacancy

class VacancyDtoConvertor {
    // класс яростного мапинга классов из слоя дата для домейн
    // могут возникнуть вопросы к мапперам работающим с лист
    fun map(vacancy: VacancyData): Vacancy {
        println(vacancy.id)
        return Vacancy(
            vacancy.id,
            vacancy.name,
            vacancy.employer?.map(),
            vacancy.salaryData?.map(),
            vacancy.adress?.map(),
            vacancy.experience?.map(),
            vacancy.employment?.map(),
            keySkillsListMap(vacancy.keySkills),
            languageList(vacancy.languages),
            driverLicenseListMap(vacancy.driverLicenseTypes),
            vacancy.area?.map(),
            vacancy.industry?.map(),
            vacancy.country?.map(),
            vacancy.contacts?.map(),
            vacancy.description
        )
    }

    fun map(vacancy: VacancyResponse): Vacancy {
        println(vacancy.id)
        return Vacancy(
            vacancy.id,
            vacancy.name,
            vacancy.employer?.map(),
            vacancy.salaryData?.map(),
            vacancy.adress?.map(),
            vacancy.experience?.map(),
            vacancy.employment?.map(),
            keySkillsListMap(vacancy.keySkills),
            languageList(vacancy.languages),
            driverLicenseListMap(vacancy.driverLicenseTypes),
            vacancy.area?.map(),
            vacancy.industry?.map(),
            vacancy.country?.map(),
            vacancy.contacts?.map(),
            vacancy.description
        )
    }

    private fun AddressData.map(): Address {
        return Address(
            this.building,
            this.city,
            this.description,
            metroStationsMap(this.metro),
            this.street,
            this.full
        )
    }


    private fun metroStationsMap(oldList: List<MetroStationsData>?): List<MetroStations>? {
        oldList?.let {
            val list = mutableListOf<MetroStations>()
            oldList.map { list.add(it.map()) }
            return list
        } ?: return null
    }

    private fun MetroStationsData.map(): MetroStations {
        return MetroStations(
            this.lineName,
            this.stationName,
        )
    }

    private fun ContactsData.map(): Contacts {
        return Contacts(
            this.email,
            this.name,
            phonesMap(this.phones)
        )
    }

    private fun PhonesData.map(): Phone {
        return Phone(
            this.city,
            this.comment,
            this.country,
            this.formatted,
            this.number
        )
    }

    private fun phonesMap(oldList: List<PhonesData>?): List<Phone>? {
        oldList?.let {
            val list = mutableListOf<Phone>()
            oldList.map { list.add(it.map()) }
            return list
        } ?: return null
    }

    private fun CountryData.map(): Country {
        return Country(
            this.id,
            this.name
        )
    }


    private fun IndustryNestedData.map(): IndustryNested {
        return IndustryNested(
            this.id,
            this.name
        )
    }


    private fun IndustryData.map(): Industry {
        return Industry(
            this.id,
            this.name,
            industryNestedMap(this.industries)
        )
    }

    private fun industryNestedMap(oldList: List<IndustryNestedData>?): List<IndustryNested>? {
        oldList?.let {
            val list = mutableListOf<IndustryNested>()
            oldList.map { list.add(it.map()) }
            return list
        } ?: return null
    }

    private fun AreaData.map(): Area {
        return Area(
            this.id,
            this.name,
            this.url
        )
    }

    private fun EmployerData.map(): Employer {
        return Employer(
            this.name,
            this.logoUrls?.map()
        )
    }

    private fun LogoUrlsData.map(): LogoUrls {
        return LogoUrls(
            this.small,
            this.medium,
            this.original
        )
    }


private fun SalaryData.map(): Salary {
    return Salary(
        this.currency,
        this.from,
        this.gross,
        this.to
    )
}

private fun ExperienceData.map(): Experience {
    return Experience(
        this.name,
    )
}

private fun EmploymentData.map(): Employment {
    return Employment(
        this.name,
    )
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
        this.level?.map()
    )
}

private fun LanguageLevelData.map(): LanguageLevel {
    return LanguageLevel(
        this.name
    )
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
}}
