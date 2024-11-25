package ru.practicum.android.diploma.ui.industry.models

import ru.practicum.android.diploma.domain.models.entity.Industry
import ru.practicum.android.diploma.domain.models.entity.IndustryNested

sealed interface IndustryFragmentState {
    data class Content(val listIndastries: List<IndustryNested>) :IndustryFragmentState
}
