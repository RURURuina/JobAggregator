package ru.practicum.android.diploma.data.dto.vacancy

import com.google.gson.annotations.SerializedName

data class VacancyData(
    // Для JobItem
    val id: String, // id вакансии
    val name: String?, // название вакансии
    val employer: EmployerData?, // работодатель
    @SerializedName("salary") val salaryData: SalaryData?, // зарплата

    // Для Описания вакансии
    val experience: ExperienceData?, // опыт
    val employment: EmploymentData?, // тип занятости
    @SerializedName("key_skills") val keySkills: List<KeySkillData>?, // ключевые навыки
    val languages: List<LanguageData>?, // языки
    @SerializedName("driver_license_types") val driverLicenseTypes: List<DriverLicenseData>?, // категория прав водителя
)

data class EmployerData(
    val name: String?, // название работодателя
    @SerializedName("logo_urls") val logoUrls: LogoUrlsData?, // Иконки работодателя
)

data class SalaryData(
    val currency: String?, // валюта
    val from: Int?, // сумма от
    val gross: Boolean?, // до или после вычета
    val to: Int?, // сумма до
)

data class LogoUrlsData(
    @SerializedName("90") val small: String?, // Маленькая иконка
    @SerializedName("240") val medium: String?, // Большая иконка
    val original: String?, // стандартная иконка
)

data class ExperienceData(
    val name: String?, // Строка "требуемый опыт"
)

data class EmploymentData(
    val name: String?, // строка "тип занятости"
)

data class KeySkillData(
    val name: String?, // Описание(Обязанности)
)

data class LanguageData(
    val name: String?, // Название языка
    val level: LanguageLevelData?,
)

data class LanguageLevelData(
    val name: String?, // Уровень языка
)

data class DriverLicenseData(
    val id: String?, // название категории
)
