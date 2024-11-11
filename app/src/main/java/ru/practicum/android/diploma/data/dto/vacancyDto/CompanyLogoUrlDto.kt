package ru.practicum.android.diploma.data.dto.vacancyDto

import com.google.gson.annotations.SerializedName

data
class CompanyLogoUrlDto (
    @SerializedName("90")
    val smallLogoUrl90: String?,
    @SerializedName("240")
    val mediumLogoUrl240: String?,
    @SerializedName("original")
    val originalLogoUrl: String?
){
}
