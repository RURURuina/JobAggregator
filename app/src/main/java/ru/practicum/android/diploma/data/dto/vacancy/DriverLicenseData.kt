package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.DriverLicense

data class DriverLicenseData(
    val id: String?, // название категории
)

fun DriverLicenseData.map(): DriverLicense {
    return DriverLicense(
        this.id
    )
}
