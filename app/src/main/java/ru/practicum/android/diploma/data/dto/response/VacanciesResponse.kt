package ru.practicum.android.diploma.data.dto.response

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.vacancyDto.VacancyDto

class VacanciesResponse(
    @SerializedName("items")
    val vacancies: List<VacancyDto>?,
    val found: Int?,
    val page: Int?,
    val pages: Int?,
) : Response()
