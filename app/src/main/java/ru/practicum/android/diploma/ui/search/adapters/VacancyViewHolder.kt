package ru.practicum.android.diploma.ui.search.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.databinding.JobItemBinding
import ru.practicum.android.diploma.domain.models.entity.Vacancy

class VacancyViewHolder(private val binding: JobItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(vacancy: Vacancy){
        binding.cardTitleText.text = vacancy.name
        binding.cardDescriptionText.text = vacancy.employer?.name
        var salaryString = ""
        if (vacancy.salary?.from != null){
            salaryString += "От ${vacancy.salary.from}${vacancy.salary.currency}"
        }
        if (vacancy.salary?.to != null){
            salaryString += " до ${vacancy.salary.to}${vacancy.salary.currency}"
        }
        if (vacancy.salary?.from == null && vacancy.salary?.to == null){
            salaryString = "Зарплата не указана"
        }
        binding.cardSalaryText.text = salaryString
        Glide.with(binding.root)
            .load(vacancy.employer?.logoUrls?.original)
            .centerInside()
            .into(binding.cardImage)
    }
}
