package ru.practicum.android.diploma.data.dto.vacancy

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.domain.models.entity.LogoUrls

data class LogoUrlsData(
    @SerializedName("90") val small: String?, // Маленькая иконка
    @SerializedName("240") val medium: String?, // Большая иконка
    val original: String?, // стандартная иконка
)

fun LogoUrlsData.map(): LogoUrls {
    return LogoUrls(
        this.small,
        this.medium,
        this.original
    )
}
