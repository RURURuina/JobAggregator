package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FavoritesVacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: FavoritesVacancyEntity)

    @Delete
    suspend fun deleteVacancy(vacancy: FavoritesVacancyEntity)

    @Query("SELECT * FROM favorites")
    fun getFavoriteVacancies(): List<FavoritesVacancyEntity>

    @Query("SELECT * FROM favorites WHERE id = :id")
    fun getFavoriteVacancyById(id: String): FavoritesVacancyEntity
}
