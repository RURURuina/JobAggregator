package ru.practicum.android.diploma.domain.api
import com.google.gson.annotations.SerializedName
data class VacancyDomain(
    //Для JobItem
    val name: String, //название вакансии
    val employer: Employer, //работодатель
    val salary: Salary, //зарплата

    //Для Описания вакансии
    val experience: Experience, //опыт
    val employment: Employment, //тип занятости
    val key_skills: List<KeySkill>?, //ключевые навыки
    val languages: List<Language>?, //языки
    val driver_license_types: List<DriverLicense> //категория прав водителя
)

data class Employer(
    val name: String, //название работодателя
    val logo_urls: LogoUrls // Иконки работодателя
)

data class Salary(
    val currency: String?, //валюта
    val from: Int?, //сумма от
    val gross: Boolean?, // до или после вычета
    val to: Int? //сумма до
)

data class LogoUrls(
    @SerializedName("90") val small: String, //Маленькая иконка
    @SerializedName("240") val medium: String, //Большая иконка
    val original: String //стандартная иконка
)

data class Experience(
    val name: String // Строка "требуемый опыт"
)

data class Employment(
    val name: String // строка "тип занятости"
)

data class KeySkill(
    val name: String // Описание(Обязанности)
)

data class Language(
    val name: String, // Название языка
    val level: LanguageLevel //уровень языка
)

data class LanguageLevel(
    val name: String //Уровень языка
)

data class DriverLicense(
    val id: String //Тип лицензии
)

