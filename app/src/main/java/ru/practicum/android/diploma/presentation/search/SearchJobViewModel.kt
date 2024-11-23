package ru.practicum.android.diploma.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.hh.HhInteractor
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.ui.search.models.VacanciesState
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatusCode
import ru.practicum.android.diploma.util.debounce
import java.net.SocketTimeoutException

class SearchJobViewModel(private val hhInteractor: HhInteractor) : ViewModel() {
    // Кто будет выполнять задание на обработку ошибок, то сообщение будет на стр 132
    companion object {
        private const val DEBOUNCE_SEARCH_TIME = 2000L
        private const val DEBOUNCE_PAGE_TIME = 300L
        private const val PAGE_SIZE = 20 // кол-во элементов на странице для отображения в RV
    }

    private val _vacanciesState = MutableLiveData<VacanciesState>()
    val vacanciesState: LiveData<VacanciesState> = _vacanciesState

    // переменные для работы с paggination
    private var currentPage = 0
    private var maxPage = 0
    private var currentQuery = ""
    private var isLastPage = false
    private var isLoading = false
    private var vacanciesList = mutableListOf<Vacancy>()

    private val searchDebounce = debounce<String>(
        delayMillis = DEBOUNCE_SEARCH_TIME,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { searchText ->
        searchVacancies(searchText.trim())
    }
    private val loadPerPageDebounce = debounce<Int>(
        delayMillis = DEBOUNCE_PAGE_TIME,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) {
        loadVacancies(true)
    }

    fun clearVacancies() {
        pushVacanciesState(VacanciesState.Hidden)
        currentPage = 0
        maxPage = 0
        currentQuery = ""
        isLastPage = false
        isLoading = false
        vacanciesList.clear()
    }

    // эта ф-ия берет запрос из EditText и запрашивает данные с сервека через hhInteractor
    private fun searchVacancies(query: String) {
        if (currentQuery != query) {
            currentPage = 0
            maxPage = 0
            isLastPage = false
            vacanciesList.clear()
            currentQuery = query
            loadVacancies(false)
        }
    }

    private fun pushVacanciesState(state: VacanciesState) {
        _vacanciesState.postValue(state)
    }

    private fun loadVacancies(perPage: Boolean) {
        isLoading = true
        val params = createParams()
        if (currentPage == 0) {
            pushVacanciesState(VacanciesState.Loading)
        }
        if (currentQuery.isNotEmpty()) {
            viewModelScope.launch {
                isLoading = true
                try {
                    hhInteractor.getVacancies(params).collect { result ->
                        handleResult(result, perPage)
                        isLoading = false
                    }
                } catch (e: SocketTimeoutException) {
                    handleErrorSocketTimeoutException(e,perPage)
                    this.coroutineContext.job.cancel()
                }
            }
        }
    }

    fun searchDebounce(changedText: String?) {
        if (changedText != null) {
            searchDebounce.invoke(changedText)
        }
    }

    // Ф-ия для Fragment для загрузки следующей страницы
    fun loadNextPage() {
        if (!isLoading && !isLastPage && currentQuery.isNotBlank()) {
            isLoading = true
            loadPerPageDebounce(currentPage++)
        }
    }

    private fun createParams(): HashMap<String, String> {
        return hashMapOf(
            "text" to currentQuery,
            "page" to currentPage.toString(),
            "per_page" to PAGE_SIZE.toString()
        )
    }

    private fun handleResult(result: Resource<List<Vacancy>>?, perPage: Boolean) {
        when (result) {
            is Resource.Success -> {
                result.currentPage?.let {
                    currentPage = result.currentPage
                }
                result.pagesCount?.let {
                    maxPage = result.pagesCount
                }
                handleSuccess(result)
            }

            is Resource.Error -> handleError(result.responseCode,perPage)
            else -> handleError(result?.responseCode,perPage)
        }
    }

    private fun handleSuccess(result: Resource<List<Vacancy>>?) {
        val newVacancies = result?.data ?: emptyList()
        isLastPage = currentPage == maxPage - 1
        if (newVacancies.isNotEmpty()) {
            vacanciesList.addAll(newVacancies)
            pushVacanciesState(
                VacanciesState.Success(
                    vacancies = vacanciesList.toList(),
                    isLastPage = isLastPage,
                    isLoading = false,
                    result?.foundedCount
                )
            )
        } else {
            pushVacanciesState(VacanciesState.Empty)
        }
    }

    private fun handleErrorSocketTimeoutException(e: SocketTimeoutException, perPage: Boolean) {
        handleError(ResponseStatusCode.Error, perPage)
        Log.e("SearchViewModel", "SocketTimeoutException, $e")
    }

    private fun handleError(responseCode: ResponseStatusCode?, perPage: Boolean) {
        pushVacanciesState(VacanciesState.Error(responseCode,perPage))
    }

}
