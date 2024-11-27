package ru.practicum.android.diploma.ui.region.model

interface SelectRegionFragmentState {
    data class Content(
        val countryName: String?,
        val areaName: String?
    ) : SelectRegionFragmentState

    data object Exit : SelectRegionFragmentState
}
