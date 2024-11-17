package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoritesVacancyEntity::class], version = 1)
 abstract class AppDatabase: RoomDatabase() {
     abstract fun favoritesVacancyDao(): FavoritesVacancyDao
}
