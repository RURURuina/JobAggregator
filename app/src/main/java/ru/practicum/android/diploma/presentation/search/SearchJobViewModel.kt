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
    private var currentQuery = ""
    private var isLastPage = false
    private var isLoading = false
    private var vacanciesList = mutableListOf<Vacancy>()

    private val searchDebounce = debounce<String>(
        delayMillis = DEBOUNCE_SEARCH_TIME,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { searchText ->
        searchVacancies(searchText)
    }
    private val loadDebounce = debounce<Int>(
        delayMillis = DEBOUNCE_PAGE_TIME,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) {
        currentPage++
        loadVacancies()
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
            loadVacancies()
        } else {
            pushVacanciesState(VacanciesState.Hidden)
        }
    }

    private fun pushVacanciesState(state: VacanciesState) {
        _vacanciesState.postValue(state)
    }

    private fun loadVacancies() {
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
                        handleResult(result)
                        isLoading = false
                        println(result)
                    }
                } catch (e: SocketTimeoutException) {
                    handleErrorSocketTimeoutException(e)
                    this.coroutineContext.job.cancel()
                }
            }
        } else {
            handleEmptyQuery()
        }

    }

    fun searchDebounce(changedText: String?) {
        if (changedText != null) {
            searchDebounce.invoke(changedText.trim())
        }
    }

    // Ф-ия для Fragment для загрузки следующей страницы
    fun loadNextPage() {
        if (!isLoading && !isLastPage && currentQuery.isNotBlank()) {
            loadDebounce(currentPage++)
        }
    }

    private fun createParams(): HashMap<String, String> {
        return hashMapOf(
            "text" to currentQuery,
            "page" to currentPage.toString(),
            "per_page" to PAGE_SIZE.toString()
        )
    }

    private fun handleResult(result: Resource<List<Vacancy>>?) {
        when (result) {
            is Resource.Success -> handleSuccess(result.data)
            is Resource.Error -> handleError(result.responseCode)
            else -> handleError(result?.responseCode)
        }
    }

    private fun handleSuccess(data: List<Vacancy>?) {
        val newVacancies = data ?: emptyList()
        isLastPage = newVacancies.size < PAGE_SIZE
        if (newVacancies.isNotEmpty()) {
            vacanciesList.addAll(newVacancies)
            pushVacanciesState(
                VacanciesState.Success(
                    vacancies = vacanciesList.toList(),
                    isLastPage = isLastPage,
                    isLoading = false
                )
            )
        } else {
            pushVacanciesState(VacanciesState.Empty)
        }
    }

    private fun handleErrorSocketTimeoutException(e: SocketTimeoutException) {
        handleError(ResponseStatusCode.ERROR)
        Log.e("SearchViewModel", "SocketTimeoutException, $e")
    }

    private fun handleError(responseCode: ResponseStatusCode?) {
        pushVacanciesState(VacanciesState.Error(responseCode))
    }

    private fun handleEmptyQuery() {
        pushVacanciesState(VacanciesState.Empty)
    }
}
