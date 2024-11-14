package ru.practicum.android.diploma.ui.search.adapters

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.databinding.JobItemBinding
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import java.util.Locale

class VacancyViewHolder(private val binding: JobItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val currencySymbols = mapOf(
        "RUR" to "₽",    // Russian Ruble
        "BYR" to "Br",   // Belarusian Ruble
        "BYN" to "Br",   // New Belarusian Ruble
        "USD" to "$",    // US Dollar
        "EUR" to "€",    // Euro
        "KZT" to "₸",    // Kazakhstani Tenge
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

    private val numberFormat = DecimalFormat("#,###").apply {
        decimalFormatSymbols = DecimalFormatSymbols(Locale.getDefault()).apply {
            groupingSeparator = ' '
        }
    }

    fun bind(vacancy: Vacancy) {
        binding.apply {
            cardTitleText.text = vacancy.name
            cardDescriptionText.text = vacancy.employer?.name

            val currencySymbol = currencySymbols[vacancy.salary?.currency] ?: vacancy.salary?.currency.orEmpty()
            val formattedFrom = vacancy.salary?.from?.let { numberFormat.format(it) }
            val formattedTo = vacancy.salary?.to?.let { numberFormat.format(it) }

            val salaryString = when {
                formattedFrom != null && formattedTo != null ->
                    "От $formattedFrom до $formattedTo $currencySymbol"
                formattedFrom != null ->
                    "От $formattedFrom $currencySymbol"
                formattedTo != null ->
                    "До $formattedTo $currencySymbol"
                else -> "Зарплата не указана"
            }

            cardSalaryText.text = salaryString

            vacancy.employer?.logoUrls?.original.let { logoUrl ->
                Glide.with(binding.root)
                    .load(logoUrl)
                    .centerInside()
                    .into(cardImage)
            }
        }
    }

}
