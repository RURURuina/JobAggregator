package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.response.IndustriesResponse
import ru.practicum.android.diploma.data.dto.vacancy.map
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.industries.IndustriesRepository
import ru.practicum.android.diploma.domain.models.entity.Industry
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatusCode

class IndustriesRepositoryImpl(
    private val networkClient: NetworkClient,
) : IndustriesRepository {
    override suspend fun getIndustriesList(): Flow<Resource<List<Industry>>> = flow {
        val response = networkClient.getIndustriesList()
        when (response.resultCode) {
            is ResponseStatusCode.NO_INTERNET -> {
                emit(Resource.Error(ResponseStatusCode.NO_INTERNET))
            }

            is ResponseStatusCode.OK -> {
                emit(
                    Resource.Success(
                        (response as IndustriesResponse).industriesRaw.map{industryData ->
                            industryData.map()
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
