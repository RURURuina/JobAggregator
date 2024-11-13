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
import ru.practicum.android.diploma.ui.search.models.VacanciesState
import ru.practicum.android.diploma.util.Resource

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
        pushVacanciesState(VacanciesState.Hidden)
    }

    // эта ф-ия берет запрос из EditText и запрашивает данные с сервека через hhInteractor
    fun searchVacancies(query: String) {
        searchJob?.cancel()

        pushVacanciesState(VacanciesState.Loading)
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_TIME) // Реализован debounce 2 сек
            hhInteractor
                .getVacancies(hashMapOf("text" to query))
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            val data = result.data ?: emptyList()
                            if (data.isEmpty()) {
                                pushVacanciesState(VacanciesState.Empty)
                            } else {
                                pushVacanciesState(VacanciesState.Success(data))
                            }
                        }

                        is Resource.Error -> {
                            pushVacanciesState(VacanciesState.Error(result.message ?: R.string.no_internet))
                        }
                    }

                }
        }
    }

    private fun pushVacanciesState(state: VacanciesState) {
        _vacanciesState.postValue(state)
    }
}
