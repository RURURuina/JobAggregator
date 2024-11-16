package ru.practicum.android.diploma.ui.details.models

import ru.practicum.android.diploma.domain.models.entity.Vacancy

sealed interface DetailsFragmentState {
    data class Content(val vacancy: Vacancy) : DetailsFragmentState
    object ERROR : DetailsFragmentState
}
