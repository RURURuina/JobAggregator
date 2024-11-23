package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.api.industries.IndustriesRepository
import ru.practicum.android.diploma.domain.models.entity.IndustryDomain
import ru.practicum.android.diploma.util.Resource

class IndustriesInteractorImpl(val industriesRepository: IndustriesRepository) : IndustriesInteractor {
    override suspend fun getIndustriesList(): Flow<Resource<List<IndustryDomain>>> {
        return industriesRepository.getIndustriesList()
    }
}
