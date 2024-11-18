package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritesVacancyEntity(
    @PrimaryKey
    val id: String, // id вакансии
    val name: String?, // название вакансии
    val employerName: String?, // название работодателя
    val logoOriginal: String?, // иконка работодателя
    val currency: String?, // валюта
    val from: Int?, // сумма от
    val to: Int?, // сумма до
)
