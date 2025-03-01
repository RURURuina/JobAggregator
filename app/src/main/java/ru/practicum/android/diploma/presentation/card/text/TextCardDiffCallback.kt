package ru.practicum.android.diploma.presentation.card.text

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.entity.Area

class TextCardDiffCallback : DiffUtil.ItemCallback<Area>() {
    override fun areItemsTheSame(oldItem: Area, newItem: Area) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Area, newItem: Area) = oldItem == newItem
}
