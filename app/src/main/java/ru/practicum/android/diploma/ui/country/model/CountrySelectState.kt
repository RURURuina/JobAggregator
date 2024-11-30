package ru.practicum.android.diploma.ui.country.model

import ru.practicum.android.diploma.domain.models.entity.Country

sealed interface CountrySelectState {
    data class Success(val countries: List<Country>) : CountrySelectState
    data object Error : CountrySelectState
    data object NoInternet : CountrySelectState
    data object Empty : CountrySelectState
    data object Exit : CountrySelectState

}
