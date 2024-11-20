package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.IndustryNested

data class IndustryNestedData(
    val id: String,
    val name: String,
)

fun IndustryNestedData.map(): IndustryNested {
    return IndustryNested(
        this.id,
        this.name
    )
}
