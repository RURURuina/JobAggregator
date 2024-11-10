package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.ui.root.VacancyResponse

interface HhApiService {
        @Headers(
            "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
            "HH-User-Agent: YpDiplomaProject"
        )
        @GET("/vacancies/")
        fun searchVacancies(
            @QueryMap params: HashMap<String, String>,
        ): VacancyResponse
}
