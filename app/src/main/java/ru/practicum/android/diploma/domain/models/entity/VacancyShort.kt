package ru.practicum.android.diploma.domain.models.entity

data class VacancyShort(
    val id: String, // id вакансии
    val name: String?, // название вакансии
    val employer: EmployerData?, // работодатель
    val salaryData: SalaryData?, // зарплата
)

data class EmployerData(
    val name: String?, // название работодателя
    val logoUrls: LogoUrlsData?, // Иконки работодателя
)

data class LogoUrlsData(
    val original: String?, // стандартная иконка
)

data class SalaryData(
    val currency: String?, // валюта
    val from: Int?, // сумма от
    val to: Int?, // сумма до
)
