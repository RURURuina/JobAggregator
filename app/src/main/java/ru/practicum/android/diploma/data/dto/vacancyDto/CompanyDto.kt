package ru.practicum.android.diploma.data.dto.vacancyDto

import com.google.gson.annotations.SerializedName

data class CompanyDto(
    val id: String,
    @SerializedName("logo_urls")
    val logoUrls: CompanyLogoUrlDto?,
    val name: String,
    val url: String?
)
