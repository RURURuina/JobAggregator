package ru.practicum.android.diploma.ui.details.models

import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.util.ResponseStatusCode

sealed interface DetailsFragmentState {
    data class Content(val vacancy: Vacancy) : DetailsFragmentState
    data class ERROR(val errState: ResponseStatusCode?) : DetailsFragmentState
}
