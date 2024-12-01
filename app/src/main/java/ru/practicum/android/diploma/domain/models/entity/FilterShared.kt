package ru.practicum.android.diploma.domain.models.entity

data class FilterShared(
    val countryName: String?,
    val countryId: String?,
    val regionName: String?,
    val regionId: String?,
    val industryName: String?,
    val industryId: String?,
    val salary: String?,
    val onlySalaryFlag: Boolean?,
)
