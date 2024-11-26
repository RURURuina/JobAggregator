package ru.practicum.android.diploma.domain.api.filter

import ru.practicum.android.diploma.domain.models.entity.FilterShared

interface FilterInteractor {
    suspend fun getFilter(): FilterShared?
    suspend fun saveFilter(filterShared: FilterShared?)
}
