package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.convertors.VacancyDtoConvertor
import ru.practicum.android.diploma.data.dto.vacancyDto.VacancyData
import ru.practicum.android.diploma.data.dto.request.VacanciesSearchRequest
import ru.practicum.android.diploma.data.dto.response.VacanciesResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.HhRepository
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.util.Resource

class HhRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyDtoConvertor: VacancyDtoConvertor,
) : HhRepository {

    override suspend fun getVacancies(expression: HashMap<String, String>): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.getVacancies(VacanciesSearchRequest(expression))

        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error(response.resultCode))
            }

            200 -> {
                emit(
                    Resource.Success(
                        (response as VacanciesResponse).vacancies!!.map { vacancyData: VacancyData ->
                            vacancyDtoConvertor.map(vacancyData)
                        }
                    ))
            }

            else -> {
                emit(Resource.Error(response.resultCode))
            }
        }
    }
}
