package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.convertors.VacancyDtoConvertor
import ru.practicum.android.diploma.data.dto.request.VacanciesSearchRequest
import ru.practicum.android.diploma.data.dto.response.VacanciesResponse
import ru.practicum.android.diploma.data.dto.vacancy.VacancyData
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.HhRepository
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatusCode

class HhRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyDtoConvertor: VacancyDtoConvertor,
) : HhRepository {

    companion object{
        private const val NO_INTERNET_CODE =-1
        private const val SUCCESS_CODE =200
        private const val MIN_SERVER_ERROR_CODE = 500
        private const val MAX_SERVER_ERROR_CODE = 599

    }

    override suspend fun getVacancies(expression: HashMap<String, String>): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.getVacancies(VacanciesSearchRequest(expression))

        when (response.resultCode) {
            is ResponseStatusCode.NO_INTERNET -> {
                emit(Resource.Error(R.string.no_internet))
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
                emit(Resource.Error(R.string.server_error))
            }

            else -> {
                emit(Resource.Error(R.string.unknown_error))
            }
        }
    }
}
