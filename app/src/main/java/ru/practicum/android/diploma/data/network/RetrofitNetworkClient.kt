package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.request.VacanciesSearchRequest
import ru.practicum.android.diploma.data.dto.request.VacancyByIdRequest
import ru.practicum.android.diploma.data.dto.response.Response
import ru.practicum.android.diploma.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.util.ResponseStatusCode
import ru.practicum.android.diploma.util.isNetworkAvailable

class RetrofitNetworkClient(
    private val hhService: HhApiService,
    private val context: Context,
) : NetworkClient {
    override suspend fun getVacancies(dto: VacanciesSearchRequest): Response {
        return withContext(Dispatchers.IO) {
            if (!isConnected()) {
                Response().apply { resultCode = ResponseStatusCode.NoInternet }
            } else {
                withContext(Dispatchers.IO) {
                    try {
                        val response = hhService.searchVacancies(
                            dto.expression
                        )
                        response.apply { resultCode = ResponseStatusCode.Ok }
                    } catch (e: HttpException) {
                        println(e)
                        Response().apply { resultCode = ResponseStatusCode.Error }
                    }
                }
            }
        }
    }

    override suspend fun getVacancyById(dto: VacancyByIdRequest): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = ResponseStatusCode.NoInternet }
        } else {
            return withContext(Dispatchers.IO) {
                try {
                    VacancyResponse(
                        hhService.searchVacancyById(
                            dto.id
                        )
                    ).apply { resultCode = ResponseStatusCode.Ok }
                } catch (e: HttpException) {
                    println(e)
                    Response().apply { resultCode = ResponseStatusCode.Error }
                }
            }
        }
    }

    override suspend fun getIndustriesList(): Response {
        if (!isConnected()) {
            // если нет интернета возврат -1
            return Response().apply { resultCode = ResponseStatusCode.NoInternet }
        } else {
            return withContext(Dispatchers.IO) {
                try {
                    val response = hhService.getIndustriesList()
                    response.apply { resultCode = ResponseStatusCode.Ok }
                } catch (e: HttpException) {
                    println(e)
                    Response().apply { resultCode = ResponseStatusCode.Error }
                }
            }
        }
    }

    override suspend fun getIndustriesList(): Response {
        if (!isConnected()) {
            // если нет интернета возврат -1
            return Response().apply { resultCode = ResponseStatusCode.NO_INTERNET }
        } else {
            return withContext(Dispatchers.IO) {
                try {
                    val response = hhService.getIndustriesList()
                    response.apply { resultCode = ResponseStatusCode.OK }
                } catch (e: HttpException) {
                    println(e)
                    Response().apply { resultCode = ResponseStatusCode.ERROR }
                }
            }
        }
    }

    private fun isConnected(): Boolean {
        return isNetworkAvailable(context)
    }
}
