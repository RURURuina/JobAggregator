package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.response.VacanciesResponse

interface HhApiService {
    // Я закоментил, потому что ПОКА(!!!) используются общедоступные функции
    /* @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: YpDiplomaProject/1.0 (4habibulin@gmail.com)"
    ) */

    @GET("vacancies")
    suspend fun searchVacancies(
        @QueryMap params: HashMap<String, String>,
    ): VacanciesResponse
}
