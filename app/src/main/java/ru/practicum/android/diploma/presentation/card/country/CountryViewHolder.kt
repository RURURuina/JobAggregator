package ru.practicum.android.diploma.presentation.card.country

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.TextItemBinding
import ru.practicum.android.diploma.domain.models.entity.Country

class CountryViewHolder(
    private val binding: TextItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(country: Country) {
        binding.cardDescriptionText.text = country.name
    }
}
