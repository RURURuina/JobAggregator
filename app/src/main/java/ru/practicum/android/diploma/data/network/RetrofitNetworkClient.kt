package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.request.VacanciesSearchRequest
import ru.practicum.android.diploma.data.dto.request.VacancyByIdRequest
import ru.practicum.android.diploma.data.dto.response.IndustriesResponse
import ru.practicum.android.diploma.data.dto.response.Response
import ru.practicum.android.diploma.util.ResponseStatusCode
import ru.practicum.android.diploma.util.isNetworkAvailable

class RetrofitNetworkClient(
    private val hhService: HhApiService,
    private val context: Context,
) : NetworkClient {
    override suspend fun getVacancies(dto: VacanciesSearchRequest): Response {
        if (!isConnected()) {
            // если нет интернета возврат
            return Response().apply { resultCode = ResponseStatusCode.NO_INTERNET }
        } else {
            return withContext(Dispatchers.IO) {
                try {
                    val response = hhService.searchVacancies(
                        dto.expression
                    )
                    response.apply { resultCode = ResponseStatusCode.OK }
                } catch (e: HttpException) {
                    println(e)
                    Response().apply { resultCode = ResponseStatusCode.ERROR }
                }
            }
        }
    }

    override suspend fun getVacancyById(dto: VacancyByIdRequest): Response {
        if (!isConnected()) {
            // если нет интернета возврат -1
            return Response().apply { resultCode = ResponseStatusCode.NO_INTERNET }
        } else {
            return withContext(Dispatchers.IO) {
                try {
                    val response = hhService.searchVacanceById(
                        dto.id
                    )
                    response.apply { resultCode = ResponseStatusCode.OK }
                } catch (e: HttpException) {
                    println(e)
                    Response().apply { resultCode = ResponseStatusCode.ERROR }
                }
            }
        }
    }

    override suspend fun getIndustriesList(): Response {
        if (!isConnected()) {
            // если нет интернета возврат -1
            return Response().apply { resultCode = ResponseStatusCode.NO_INTERNET }
        }
        return withContext(Dispatchers.IO) {
            try {
                val industriesList = hhService.getIndustriesList()
                IndustriesResponse.fromList(industriesList).apply {
                    resultCode = ResponseStatusCode.OK
                }
            } catch (e: Exception) {
                println(e)
                Response().apply {
                    resultCode = ResponseStatusCode.ERROR
                }
            }
        }
    }

    private fun isConnected(): Boolean {
        return isNetworkAvailable(context)
    }
}
