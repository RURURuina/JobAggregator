package ru.practicum.android.diploma.data.convertors

import ru.practicum.android.diploma.data.dto.vacancy.VacancyData
import ru.practicum.android.diploma.domain.models.entity.Vacancy

class VacancyDtoConvertor {
    fun map(vacancy: VacancyData): Vacancy {
        return Vacancy(
            vacancy.id,
            vacancy.name,
            vacancy.employer,
            vacancy.salary,
            vacancy.experience,
            vacancy.employment,
            vacancy.keySkills,
            vacancy.languages,
            vacancy.driverLicenseTypes,
        )
    }
}
