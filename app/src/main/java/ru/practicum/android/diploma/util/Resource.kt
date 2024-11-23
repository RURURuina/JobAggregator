package ru.practicum.android.diploma.util

sealed class Resource<T>(
    val data: T? = null,
    val responseCode: ResponseStatusCode? = null,
    val currentPage: Int? = 0,
    val pagesCount: Int? = 0,
    val foundedCount: Int? = 0
) {
    class Success<T>(
        data: T,
        responseCode: ResponseStatusCode? = null,
        currentPage: Int? = null,
        pagesCount: Int? = null,
        foundedCount: Int? = null
    ) : Resource<T>(
        data,
        responseCode,
        currentPage,
        pagesCount,
        foundedCount
    )

    class Error<T>(responseCode: ResponseStatusCode, data: T? = null) : Resource<T>(data, responseCode)
}
