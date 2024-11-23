package ru.practicum.android.diploma.domain.api.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.entity.Industry
import ru.practicum.android.diploma.util.Resource

interface IndustriesRepository {
    suspend fun getIndustriesList(): Flow<Resource<List<Industry>>>
}
