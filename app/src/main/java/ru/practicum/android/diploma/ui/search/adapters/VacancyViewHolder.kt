package ru.practicum.android.diploma.ui.search.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.databinding.JobItemBinding
import ru.practicum.android.diploma.domain.models.entity.Vacancy

class VacancyViewHolder(private val binding: JobItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(vacancy: Vacancy) {
        binding.apply {
            cardTitleText.text = vacancy.name
            cardDescriptionText.text = vacancy.employer?.name

            val salaryString = when {
                vacancy.salary?.from != null && vacancy.salary.to != null ->
                    "От ${vacancy.salary.from} до ${vacancy.salary.to} ${vacancy.salary.currency}"
                vacancy.salary?.from != null ->
                    "От ${vacancy.salary.from} ${vacancy.salary.currency}"
                vacancy.salary?.to != null ->
                    "До ${vacancy.salary.to} ${vacancy.salary.currency}"
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
