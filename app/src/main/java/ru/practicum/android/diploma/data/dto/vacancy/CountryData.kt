package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.Country

data class CountryData(
    val id: String,
    val name: String,
)

fun CountryData.map(): Country {
    return Country(
        this.id,
        this.name
    )
}
