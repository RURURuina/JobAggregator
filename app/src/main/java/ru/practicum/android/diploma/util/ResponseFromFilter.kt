package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.entity.FilterShared

fun FilterShared.getResponseWithFilters(text: String, page: Int?, pageSize: Int?): HashMap<String, String> {
    return hashMapOf(
        "text" to text,
        "page" to page.toString(),
        "per_page" to pageSize.toString(),
        "area" to this.regionId.toString(),
        "industry" to this.industryId.toString(),
        "salary" to this.salary.toString(),
        "only_with_salary" to this.onlySalaryFlag.toString()
    )
}
