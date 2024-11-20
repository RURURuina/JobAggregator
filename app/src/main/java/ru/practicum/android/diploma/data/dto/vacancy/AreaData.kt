package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.Area

data class AreaData(
    val id: String?,
    val name: String?,
    val url: String?,
)

fun AreaData.map(): Area {
    return Area(
        this.id,
        this.name,
        this.url
    )
}
