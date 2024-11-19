package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritesVacancyEntity(
//    @PrimaryKey
//    val id: String, // id вакансии
//    val name: String?, // название вакансии
//    val employerName: String?, // название работодателя
//    val logoOriginal: String?, // иконка работодателя
//    val currency: String?, // валюта
//    val from: Int?, // сумма от
//    val to: Int?, // сумма до

    @PrimaryKey
    val id: String,
    val name: String?,
    val employer: String?,
    val salary: String?,
    val address: String?,
    val experience: String?,
    val employment: String?,
    val keySkills: String?,
    val languages: String?,
    val driverLicenseTypes: String?,
    val area: String?,
    val industry: String?,
    val country: String?,
    val contacts: String?,
    val description: String?,
    val schedule: String?,
    val url: String?
)
