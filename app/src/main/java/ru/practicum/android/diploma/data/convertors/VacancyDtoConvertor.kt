package ru.practicum.android.diploma.data.convertors

import ru.practicum.android.diploma.data.dto.vacancyDto.VacancyDto
import ru.practicum.android.diploma.domain.models.entity.Vacancy

class VacancyDtoConvertor {
    fun map(vacancy: VacancyDto): Vacancy {
        return Vacancy(
            vacancy.id,
            vacancy.name
        )
    }
}
