package ru.practicum.android.diploma.data.dto.vacancy

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.domain.models.entity.MetroStations

data class MetroStationsData(
    @SerializedName("line_name")
    val lineName: String?,
    @SerializedName("station_name")
    val stationName: String?,
)

private fun MetroStationsData.map(): MetroStations {
    return MetroStations(
        this.lineName,
        this.stationName,
    )
}

fun List<MetroStationsData>?.map(): List<MetroStations>? {
    return this?.let {
        val list = mutableListOf<MetroStations>()
        this.map { list.add(it.map()) }
        list
    }
}
