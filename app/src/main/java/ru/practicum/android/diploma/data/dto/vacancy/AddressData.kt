package ru.practicum.android.diploma.data.dto.vacancy

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.domain.models.entity.Address

data class AddressData(
    val building: String?,
    val city: String?,
    val description: String?,
    @SerializedName("metro_stations")
    val metro: List<MetroStationsData>?,
    val street: String?,
    @SerializedName("raw")
    val full: String?,
)

fun AddressData.map(): Address {
    return Address(
        this.building,
        this.city,
        this.description,
        this.metro.map(),
        this.street,
        this.full
    )
}
