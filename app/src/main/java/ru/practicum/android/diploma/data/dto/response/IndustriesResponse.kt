package ru.practicum.android.diploma.data.dto.response

import ru.practicum.android.diploma.data.dto.vacancy.IndustryData

data class IndustriesResponse(
    val industriesRaw: List<IndustryData>
) : Response() {
    companion object {
        fun fromList(list : List<IndustryData>): IndustriesResponse {
            return IndustriesResponse(list)
        }
    }
}
