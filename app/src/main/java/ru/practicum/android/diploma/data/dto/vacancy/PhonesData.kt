package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.Phone

class PhonesData(
    val city: String,
    val comment: String? = null,
    val country: String,
    val formatted: String,
    val number: String,
)

fun PhonesData.map(): Phone {
    return Phone(
        this.city,
        this.comment,
        this.country,
        this.formatted,
        this.number
    )
}
