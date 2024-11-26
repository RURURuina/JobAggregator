package ru.practicum.android.diploma.presentation.card.country

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.entity.Country

class CountryDiffCallback : DiffUtil.ItemCallback<Country>() {
    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }
}
