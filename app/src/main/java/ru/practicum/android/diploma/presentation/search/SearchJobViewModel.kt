package ru.practicum.android.diploma.presentation.search

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.hh.HhInteractor
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.ui.search.models.VacanciesState
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.debounce


class SearchJobViewModel(private val hhInteractor: HhInteractor) : ViewModel() {
    // Кто будет выполнять задание на обработку ошибок, то сообщение будет на стр 132
    companion object {
        private const val DEBOUNCE_TIME = 2000L
        private const val PAGE_SIZE = 20 // кол-во элементов на странице для отображения в RV
    }

    private val _vacanciesState = MutableLiveData<VacanciesState>()
    val vacanciesState: LiveData<VacanciesState> = _vacanciesState

    private var lastSearchText: String? = null
    private var searchJob: Job? = null

    // переменные для работы с paggination
    private var currentPage = 0
    private var currentQuery = ""
    private var isLastPage = false
    private var isLoading = false
    private var vacanciesList = mutableListOf<Vacancy>()

    private val searchDebounced = debounce<Any>(DEBOUNCE_TIME, viewModelScope, true) { String ->
        viewModelScope.launch {
            loadVacancies()
        }
    }

    init {
        clearVacancies()

    }

    fun clearVacancies() {
        pushVacanciesState(VacanciesState.Hidden)
        currentPage = 0
        currentQuery = ""
        isLastPage = false
        isLoading = false
        vacanciesList.clear()
    }

    // эта ф-ия берет запрос из EditText и запрашивает данные с сервека через hhInteractor
    private fun searchVacancies(query: String) {
        if (currentQuery != query) {
            currentPage = 0
            isLastPage = false
            vacanciesList.clear()
            currentQuery = query
        }
        if (query.isNotBlank()) {
            searchJob?.cancel()
            run(searchDebounced)
        } else {
            pushVacanciesState(VacanciesState.Hidden)
        }
    }

    private fun pushVacanciesState(state: VacanciesState) {
        _vacanciesState.postValue(state)
    }

    private suspend fun loadVacancies() {
        Log.d(TAG, "loadVacancies: starting")
        isLoading = true
        val params = hashMapOf(
            "text" to currentQuery,
            "page" to currentPage.toString(),
            "per_page" to PAGE_SIZE.toString()
        )
        if (currentPage == 0) {
            pushVacanciesState(VacanciesState.Loading)
        }
        if (currentQuery.isNotEmpty()) {

            hhInteractor.getVacancies(params).map { result ->
                when (result) {
                    is Resource.Success -> {
                        val newVacancies = result.data ?: emptyList()
                        isLastPage = newVacancies.size < PAGE_SIZE
                        if (newVacancies.isEmpty()) {
                            pushVacanciesState(VacanciesState.Empty)
                        } else {
                            vacanciesList.addAll(newVacancies)
                            pushVacanciesState(
                                VacanciesState.Success(
                                    vacancies = vacanciesList.toList(),
                                    isLastPage = isLastPage,
                                    isLoading = false
                                )
                            )
                        }
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "loadVacancies: Resource.Error")
                        if (isLoading) {
                            pushVacanciesState(VacanciesState.Error(result.responseCode))
                        }
                    }
                }
                isLoading = false
            }
        }

    }

    fun searchDebounce(changedText: String) {
        if (lastSearchText != changedText) {
            lastSearchText = changedText
            searchVacancies(changedText)
        }
    }

    // Ф-ия для Fragment для загрузки следующей страницы
    fun loadNextPage() {
        if (!isLoading && !isLastPage && currentQuery.isNotBlank()) {
            currentPage++
            viewModelScope.launch {
                loadVacancies()
            }
        }
    }
}
