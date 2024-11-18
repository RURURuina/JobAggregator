package ru.practicum.android.diploma.ui.search.models

import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.util.ResponseStatusCode

sealed interface VacanciesState {
    object Loading : VacanciesState // Состояние загрузки - видно progress bar
    // Добавил в Success новые переменные для работы с paggination
    data class Success( // состояние успешно загруженных данных - видно RV
        val vacancies: List<Vacancy>,
        val isLastPage: Boolean, // последняя страница
        val isLoading: Boolean // состояние загрузки
        ) : VacanciesState
    data class Error(val responseState: ResponseStatusCode?) : VacanciesState // состояние ошибки - видно placeHolder ошибки
    object Empty : VacanciesState // Состояние "Не найдено" - видно placeHolder
    object Hidden : VacanciesState // Состояние отсутствие запроса - показан стартовый placeHolder
}
