package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.IndustryDomain
import ru.practicum.android.diploma.domain.models.entity.IndustryNested

data class IndustryData(
    val id: String,
    val name: String,
    val industries: List<IndustryNestedData>?,
)

fun IndustryData.map(): IndustryDomain {
    return IndustryDomain(
        this.id,
        this.name,
        this.industries.map()
    )
}

private fun List<IndustryNestedData>?.map(): List<IndustryNested>? {
    this?.let {
        val list = mutableListOf<IndustryNested>()
        this.map { list.add(it.map()) }
        return list
    } ?: return null
}
