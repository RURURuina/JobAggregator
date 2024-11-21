package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.convertors.VacancyDtoConvertor
import ru.practicum.android.diploma.data.dto.request.VacanciesSearchRequest
import ru.practicum.android.diploma.data.dto.request.VacancyByIdRequest
import ru.practicum.android.diploma.data.dto.response.VacanciesResponse
import ru.practicum.android.diploma.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.data.dto.vacancy.VacancyData
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.hh.HhRepository
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatusCode

class HhRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyDtoConvertor: VacancyDtoConvertor,
) : HhRepository {

    override suspend fun getVacancies(expression: HashMap<String, String>): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.getVacancies(VacanciesSearchRequest(expression))

        when (response.resultCode) {
            is ResponseStatusCode.NO_INTERNET -> {
                emit(Resource.Error(ResponseStatusCode.NO_INTERNET))
            }

            is ResponseStatusCode.OK -> {
                emit(
                    Resource.Success(
                        (response as VacanciesResponse).vacancies!!.map { vacancyData: VacancyData ->
                            vacancyDtoConvertor.map(vacancyData)
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

    override suspend fun searchVacanceById(id: String): Flow<Resource<Vacancy>> = flow {
        val response = networkClient.getVacancyById(VacancyByIdRequest(id))
        when (response.resultCode) {
            is ResponseStatusCode.NO_INTERNET -> {
                emit(Resource.Error(ResponseStatusCode.NO_INTERNET))
            }

            is ResponseStatusCode.OK -> {
                val resultRaw = response as VacancyResponse
                emit(
                    Resource.Success(vacancyDtoConvertor.run { map(resultRaw) })
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
