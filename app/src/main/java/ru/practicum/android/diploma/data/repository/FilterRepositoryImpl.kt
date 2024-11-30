package ru.practicum.android.diploma.data.repository

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.convertors.SharedStringConvertor
import ru.practicum.android.diploma.domain.api.filter.FilterRepository
import ru.practicum.android.diploma.domain.models.entity.FilterShared

class FilterRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val sharedStringConvertor: SharedStringConvertor
) : FilterRepository {
    override suspend fun getTempFilter(): FilterShared? {
        return withContext(Dispatchers.IO) {
            val str = sharedPreferences.getString(KEY_TEMP, null)
            sharedStringConvertor.getFilterShared(str)
        }
    }

    override suspend fun saveTempFilter(filterShared: FilterShared?) {
        withContext(Dispatchers.IO) {
            val str = sharedStringConvertor.getStringForShared(filterShared)
            sharedPreferences.edit()
                .putString(KEY_TEMP, str)
                .apply()
        }
    }

    override suspend fun getFilter(): FilterShared? {
        return withContext(Dispatchers.IO) {
            val str = sharedPreferences.getString(KEY_FILTER, null)
            sharedStringConvertor.getFilterShared(str)
        }
    }

    override suspend fun saveFilter(filterShared: FilterShared?) {
        withContext(Dispatchers.IO) {
            val str = sharedStringConvertor.getStringForShared(filterShared)
            sharedPreferences.edit()
                .putString(KEY_FILTER, str)
                .apply()
        }
    }

    private companion object {
        const val KEY_TEMP = "Filter_temp"
        const val KEY_FILTER = "Filter"
    }

}
