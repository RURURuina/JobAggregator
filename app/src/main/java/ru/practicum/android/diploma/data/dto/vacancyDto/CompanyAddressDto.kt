package ru.practicum.android.diploma.data.dto.vacancyDto

import com.google.gson.annotations.SerializedName

data class CompanyAddressDto(
    val building: String?,
    val city: String?,
    val description: String?,
    @SerializedName("metro_stations")
    val metro: List<MetroStationsDto>?,
    val street: String?,
    @SerializedName("raw")
    val full: String?
)
