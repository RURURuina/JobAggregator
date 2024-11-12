package ru.practicum.android.diploma.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.JobItemBinding
import ru.practicum.android.diploma.domain.models.entity.Vacancy

class VacancyAdapter : ListAdapter<Vacancy, VacancyViewHolder>(VacancyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val binding = JobItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VacancyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
