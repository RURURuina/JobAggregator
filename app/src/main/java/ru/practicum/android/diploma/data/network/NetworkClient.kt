package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.request.VacanciesSearchRequest

interface NetworkClient {
    suspend fun getVacancies(dto: VacanciesSearchRequest): ru.practicum.android.diploma.data.dto.response.Response
}
