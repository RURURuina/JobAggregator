package ru.practicum.android.diploma.presentation.card.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.TextItemBinding
import ru.practicum.android.diploma.domain.models.entity.Country

class CountryAdapter : ListAdapter<Country, CountryViewHolder>(CountryDiffCallback()) {
    var onItemClick = {_: Country -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = TextItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = getItem(position)
        holder.bind(country)
        holder.itemView.setOnClickListener { onItemClick.invoke(getItem(position)) }
    }
}
