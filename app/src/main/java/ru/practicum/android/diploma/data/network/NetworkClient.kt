package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.ui.root.Response

interface NetworkClient {
    suspend fun getVacancies(dto:Any):Response
}
