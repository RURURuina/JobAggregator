package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.request.VacanciesSearchRequest
import ru.practicum.android.diploma.data.dto.response.Response

class RetrofitNetworkClient(
    private val hhService: HhApiService,
    private val context: Context,
) : NetworkClient {
    override suspend fun getVacancies(dto: VacanciesSearchRequest): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        } else {
            return withContext(Dispatchers.IO) {
                try {
                    val response = hhService.searchVacancies(
                        dto.expression
                    )
                    response.apply { resultCode = 200 }
                } catch (e: HttpException) {
                    println(e)
                    Response().apply { resultCode = e.code() }
                }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}
