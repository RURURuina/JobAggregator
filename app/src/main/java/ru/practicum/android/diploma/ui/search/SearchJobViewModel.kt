package ru.practicum.android.diploma.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.HhInteractor
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.util.Resource

class SearchJobViewModel(private val hhInteractor: HhInteractor) : ViewModel() {

    companion object {
        private const val DEBOUNCE_TIME = 2000L
    }

    private val _vacancies = MutableLiveData<List<Vacancy>>() // Отслеживаем входящие вакансии
    val vacancies: LiveData<List<Vacancy>> = _vacancies

    private var searchJob: Job? = null

    // в этой функции тестим поиск
    fun start() {
        searchVacancies("Повар")
    }

    // эта ф-ия берет запрос из EditText и запрашивает данные с сервека через hhInteractor
    fun searchVacancies(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_TIME) // Реализован debounce 2 сек
            hhInteractor.getVacancies(hashMapOf("text" to query)).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _vacancies.value = result.data ?: emptyList()
                        result.data?.map {
                            Log.d("SearchJobViewModel", "$it ")
                        }
                    }

                    is Resource.Error -> {
                        // Если ошибка - прокинуть как переменную в UI
                    }
                }

            }
        }
    }
}

