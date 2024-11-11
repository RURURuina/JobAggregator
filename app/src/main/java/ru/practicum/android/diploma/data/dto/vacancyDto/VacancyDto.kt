package ru.practicum.android.diploma.data.dto.vacancyDto

data class VacancyDto(
    val id: String,
    val name: String?,
    val salary: SalaryDto?,
    val employer: CompanyDto?,
    val address: CompanyAddressDto?,
    val area: SearchAreaDto?
    )
