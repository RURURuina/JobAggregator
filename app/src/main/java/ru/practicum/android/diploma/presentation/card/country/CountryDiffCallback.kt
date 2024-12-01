package ru.practicum.android.diploma.presentation.card.country

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.entity.Area
import ru.practicum.android.diploma.domain.models.entity.Country

class CountryDiffCallback : DiffUtil.ItemCallback<Area>() {
    override fun areItemsTheSame(oldItem: Area, newItem: Area): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Area, newItem: Area): Boolean {
        return oldItem == newItem
    }
}
