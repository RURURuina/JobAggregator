package ru.practicum.android.diploma.data.dto.vacancy

import com.google.gson.annotations.SerializedName

data class VacancyData(
    // Для JobItem
    val id: String, // id вакансии
    val name: String?, // название вакансии
    val employer: EmployerData?, // работодатель
    @SerializedName("salary") val salaryData: SalaryData?, // зарплата
    val adress: AddressData?,
    // Для Описания вакансии
    val experience: ExperienceData?, // опыт
    val employment: EmploymentData?, // тип занятости
    @SerializedName("key_skills") val keySkills: List<KeySkillData>?, // ключевые навыки
    val languages: List<LanguageData>?, // языки
    @SerializedName("driver_license_types") val driverLicenseTypes: List<DriverLicenseData>?, // категория прав водителя
    val area: AreaData?,
    val industry: IndustryData?,
    val country: CountryData?,
    val contacts: ContactsData?,
    val description: String?,
    val schedule: ScheduleData?,
    @SerializedName("alternate_url") val url: String?,

    )


