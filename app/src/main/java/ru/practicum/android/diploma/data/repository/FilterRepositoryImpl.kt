package ru.practicum.android.diploma.data.repository

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.convertors.SharedStringConvertor
import ru.practicum.android.diploma.domain.api.filter.FilterRepository
import ru.practicum.android.diploma.domain.models.entity.FilterShared

class FilterRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val sharedStringConvertor: SharedStringConvertor
) : FilterRepository {
    override suspend fun getFilter(): FilterShared? {
        return withContext(Dispatchers.IO) {
            val str = sharedPreferences.getString(KEY, null)
            Log.d("FilterRepository", "getFilter: $str")
            sharedStringConvertor.getFilterShared(str)
        }
    }

    override suspend fun saveFilter(filterShared: FilterShared?) {
        withContext(Dispatchers.IO) {
            val str = sharedStringConvertor.getStringForShared(filterShared)
            Log.d("FilterRepository", "saveFilter: $str")
            sharedPreferences.edit()
                .putString(KEY, str)
                .apply()
        }
    }
    private companion object {
        const val KEY = "Filter"
    }
}
