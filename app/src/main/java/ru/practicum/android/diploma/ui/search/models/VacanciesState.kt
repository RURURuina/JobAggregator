package ru.practicum.android.diploma.ui.search.models

import ru.practicum.android.diploma.domain.models.entity.Vacancy

sealed interface VacanciesState {
    object Loading : VacanciesState
    data class Success(val vacancies: List<Vacancy>) : VacanciesState
    data class Error(val message: Int) : VacanciesState
    object Empty : VacanciesState
    object Hidden : VacanciesState
}
