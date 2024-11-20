package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.LanguageLevel

data class LanguageLevelData(
    val name: String?, // Уровень языка
)

fun LanguageLevelData.map(): LanguageLevel {
    return LanguageLevel(
        this.name
    )
}
