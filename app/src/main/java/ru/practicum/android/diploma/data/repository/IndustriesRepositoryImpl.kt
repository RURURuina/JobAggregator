package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.convertors.IndustryDtoConverter
import ru.practicum.android.diploma.data.dto.response.IndustriesResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.Industries.IndustriesRepository
import ru.practicum.android.diploma.domain.models.entity.IndustryDomain
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatusCode

class IndustriesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val industryDtoConverter: IndustryDtoConverter
) : IndustriesRepository {
    override suspend fun getIndustriesList(): Flow<Resource<List<IndustryDomain>>> = flow {
        val response = networkClient.getIndustriesList()
        when (response.resultCode) {
            is ResponseStatusCode.NO_INTERNET -> {
                emit(Resource.Error(ResponseStatusCode.NO_INTERNET))
            }

            is ResponseStatusCode.OK -> {
                val industryWrapper = (response as IndustriesResponse).industriesRaw
                emit(
                    Resource.Success(
                        industryWrapper.flatMap { industryWrapper ->
                            industryWrapper.industries.map{ industryData ->
                                industryDtoConverter.map(industryData)
                            }
                        }
                    )
                )
            }

            is ResponseStatusCode.ERROR -> {
                emit(Resource.Error(ResponseStatusCode.ERROR))
            }

            else -> {
                emit(Resource.Error(ResponseStatusCode.ERROR))
            }
        }
    }
}
