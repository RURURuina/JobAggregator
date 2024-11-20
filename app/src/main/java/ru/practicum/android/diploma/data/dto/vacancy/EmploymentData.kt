package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.Employment

data class EmploymentData(
    val name: String?, // строка "тип занятости"
)

fun EmploymentData.map(): Employment {
    return Employment(
        this.name,
    )
}
