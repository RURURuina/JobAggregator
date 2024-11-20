package ru.practicum.android.diploma.util

sealed class Resource<T>(val data: T? = null, val responseCode: ResponseStatusCode? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(responseCode: ResponseStatusCode, data: T? = null) : Resource<T>(data, responseCode)
}
