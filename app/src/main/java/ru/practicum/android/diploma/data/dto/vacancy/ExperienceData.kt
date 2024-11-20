package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.Experience

data class ExperienceData(
    val name: String?, // Строка "требуемый опыт"
)

fun ExperienceData.map(): Experience {
    return Experience(
        this.name,
    )
}
