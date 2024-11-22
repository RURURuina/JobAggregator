package ru.practicum.android.diploma.util

sealed class Resource<T>(val data: T? = null, val responseCode: ResponseStatusCode? = null) {
    class Success<T>(data: T, responseCode: ResponseStatusCode? = null) : Resource<T>(data, responseCode)
    class Error<T>(responseCode: ResponseStatusCode, data: T? = null) : Resource<T>(data, responseCode)
}
