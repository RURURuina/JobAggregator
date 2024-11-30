package ru.practicum.android.diploma.domain.api.filter

import ru.practicum.android.diploma.domain.models.entity.FilterShared

interface FilterRepository {
    suspend fun getTempFilter(): FilterShared?
    suspend fun saveTempFilter(filterShared: FilterShared?)
    suspend fun getFilter(): FilterShared?
    suspend fun saveFilter(filterShared: FilterShared?)
}
