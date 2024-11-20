package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.Language

data class LanguageData(
    val name: String?, // Название языка
    val level: LanguageLevelData?,
)

fun LanguageData.map(): Language {
    return Language(
        this.name,
        this.level?.map()
    )
}
