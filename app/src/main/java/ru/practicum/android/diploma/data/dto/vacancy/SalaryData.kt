package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.entity.Salary

data class SalaryData(
    val currency: String?, // валюта
    val from: Int?, // сумма от
    val gross: Boolean?, // до или после вычета
    val to: Int?, // сумма до
)

fun SalaryData.map(): Salary {
    return Salary(
        this.currency,
        this.from,
        this.gross,
        this.to
    )
}
