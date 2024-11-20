package ru.practicum.android.diploma.util

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import ru.practicum.android.diploma.domain.models.entity.Salary
import java.util.Locale

private val currencySymbols = mapOf(
    "RUR" to "₽", // Russian Ruble
    "BYR" to "Br", // Belarusian Ruble
    "BYN" to "Br", // New Belarusian Ruble
    "USD" to "$",  // US Dollar
    "EUR" to "€", // Euro
    "KZT" to "₸", // Kazakhstani Tenge
    "UZS" to "so'm", // Uzbekistani So'm
    "CNY" to "¥",    // Chinese Yuan
    "GBP" to "£",    // British Pound
    "JPY" to "¥",    // Japanese Yen
    "TRY" to "₺",    // Turkish Lira
    "INR" to "₹",    // Indian Rupee
    "AED" to "د.إ",  // UAE Dirham
    "CAD" to "$",    // Canadian Dollar
    "AUD" to "$",    // Australian Dollar
    "CHF" to "₣",    // Swiss Franc
    "PLN" to "zł",   // Polish Zloty
)

fun Salary?.format(): String {
    return this?.let {
        "${this.from?.formattedFrom()}${this.to?.formattedTo()} ${this.currency.currencySymbol()}"
    } ?: "Зарплата не указана"
}

private fun Int.numberFormater(): String {
    return DecimalFormat("#,###").apply {
        decimalFormatSymbols = DecimalFormatSymbols(Locale.getDefault()).apply {
            groupingSeparator = ' '
        }
    }.format(this)
}
private fun String?.currencySymbol():String{
    return currencySymbols[this] ?: this.orEmpty()
}

private fun Int?.formattedFrom():String{
    return this?.let { "от ${it.numberFormater()}" } ?: ""
}
private fun Int?.formattedTo():String{
    return this?.let { " до ${it.numberFormater()}" } ?: ""
}
