package ru.practicum.android.diploma.data.dto.response

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.vacancy.AreaData

class CityResponse(
    val id:String? = null,
    val name:String? = null,
    @SerializedName("areas")
    val areas: List<AreaData>
) : Response()
