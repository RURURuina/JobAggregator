package ru.practicum.android.diploma.data.dto.vacancyDto

import com.google.gson.annotations.SerializedName

data class MetroStationsDto(
    @SerializedName("line_name")
    val lineName: String?,
    @SerializedName("station_name")
    val stationName: String?
) {
}
