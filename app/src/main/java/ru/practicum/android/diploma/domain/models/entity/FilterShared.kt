package ru.practicum.android.diploma.domain.models.entity

data class FilterShared(
    val countryName: String?,
    val countryId: String?,
    val regionName: String?,
    val regionId: String?,
    val industryName: String?,
    val industryId: String?,
    val salary: String?,
    val onlySalaryFlag: Boolean?
) {
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
}
