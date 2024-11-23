package ru.practicum.android.diploma.domain.api.Industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.entity.IndustryDomain
import ru.practicum.android.diploma.util.Resource

interface IndustriesInteractor {
    suspend fun getIndustriesList(): Flow<Resource<List<IndustryDomain>>>
}
