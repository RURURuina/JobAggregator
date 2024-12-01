package ru.practicum.android.diploma.presentation.card.country

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.TextItemBinding
import ru.practicum.android.diploma.domain.models.entity.Area
import ru.practicum.android.diploma.domain.models.entity.Country

class CountryViewHolder(
    private val binding: TextItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(country: Area) {
        binding.cardDescriptionText.text = country.name
    }
}
