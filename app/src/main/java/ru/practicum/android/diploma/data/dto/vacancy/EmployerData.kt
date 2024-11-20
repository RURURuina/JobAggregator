package ru.practicum.android.diploma.data.dto.vacancy

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.domain.models.entity.Employer

data class EmployerData(
    val name: String?, // название работодателя
    @SerializedName("logo_urls") val logoUrls: LogoUrlsData?, // Иконки работодателя
)

fun EmployerData.map(): Employer {
    return Employer(
        this.name,
        this.logoUrls?.map()
    )
}
