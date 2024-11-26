package ru.practicum.android.diploma.ui.industry.models

import ru.practicum.android.diploma.domain.models.entity.IndustryNested

sealed interface IndustryFragmentState {
    data class Content(val listIndastries: List<IndustryNested>, val checkedIndustry: IndustryNested?) :
        IndustryFragmentState

    data class Filter(val listIndastries: List<IndustryNested>, val checkedIndustry: IndustryNested?) :
        IndustryFragmentState

    data object Exit : IndustryFragmentState
}
