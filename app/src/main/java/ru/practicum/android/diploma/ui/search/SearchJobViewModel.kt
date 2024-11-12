package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.HhInteractor
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.util.Resource

sealed class VacanciesState {
    object Loading : VacanciesState()
    data class Success(val vacancies: List<Vacancy>) : VacanciesState()
    data class Error(val message: Int) : VacanciesState()
    object Empty : VacanciesState()
    object Hidden : VacanciesState()
}

class SearchJobViewModel(private val hhInteractor: HhInteractor) : ViewModel() {

    companion object {
        private const val DEBOUNCE_TIME = 2000L
    }

    private val _vacanciesState = MutableLiveData<VacanciesState>()
    val vacanciesState: LiveData<VacanciesState> = _vacanciesState

    private var searchJob: Job? = null

    init {
        clearVacancies()
    }

    fun clearVacancies() {
        _vacanciesState.value = VacanciesState.Hidden
    }

    // эта ф-ия берет запрос из EditText и запрашивает данные с сервека через hhInteractor
    fun searchVacancies(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_TIME) // Реализован debounce 2 сек
            hhInteractor.getVacancies(hashMapOf("text" to query)).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _vacanciesState.value = VacanciesState.Success(result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _vacanciesState.value = VacanciesState.Error(result.message ?: R.string.no_internet)
                    }
                }

            }
        }
    }
}
