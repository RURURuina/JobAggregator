package ru.practicum.android.diploma.data.dto.response

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.vacancy.VacancyData

class VacanciesResponse(
    @SerializedName("items")
    val vacancies: List<VacancyData>,
    val found: Int?,
    val page: Int?,
    val pages: Int?,
) : Response()
