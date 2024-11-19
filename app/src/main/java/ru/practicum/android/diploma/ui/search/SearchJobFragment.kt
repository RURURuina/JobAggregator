package ru.practicum.android.diploma.ui.search

import android.content.ContentValues.TAG
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchJobBinding
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.presentation.search.SearchJobViewModel
import ru.practicum.android.diploma.ui.root.RootActivity.Companion.VACANCY_TRANSFER_KEY
import ru.practicum.android.diploma.ui.search.adapters.VacancyAdapter
import ru.practicum.android.diploma.ui.search.models.VacanciesState
import ru.practicum.android.diploma.util.ResponseStatusCode
import ru.practicum.android.diploma.util.debounce

class SearchJobFragment : Fragment() {
    private companion object {
        const val CLICK_DEBOUNCE_DELAY = 2000L
    }

    private var binding: FragmentSearchJobBinding? = null
    private val viewModel: SearchJobViewModel by viewModel()
    private val vacancyAdapter = VacancyAdapter()
    private var scrollListener: RecyclerView.OnScrollListener? = null
    private var onItemClick: ((Vacancy) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchJobBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEditText()
        initRecyclerView()
        observeViewModel()
        prepareOnItemClick()
    }

    private fun prepareOnItemClick() {
        onItemClick = debounce(
            delayMillis = CLICK_DEBOUNCE_DELAY,
            coroutineScope = viewLifecycleOwner.lifecycleScope,
            useLastParam = true
        ) { vacancy ->
            val bundle = bundleOf(VACANCY_TRANSFER_KEY to vacancy.id)
            /*vacancy id нужно будет вынести в компаньон обджект в апп*/
            findNavController().navigate(R.id.action_searchJobFragment_to_detailsFragment, bundle)
        }

    }

    private fun initEditText() {
        binding?.searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // функция не используется
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSearchIcon(s.isNullOrEmpty())
                if (!s.isNullOrBlank()) {
                    updateRecyclerView(emptyList())
                    viewModel.searchDebounce(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.isEmpty() == true) {
                    viewModel.clearVacancies()
                }
            }
        })

        binding?.clearSearchButton?.setOnClickListener {
            binding?.searchEditText?.text?.clear()
            viewModel.clearVacancies()
        }
    }

    private fun initRecyclerView() {
        binding?.vacanciesRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = vacancyAdapter

            scrollListener?.let { removeOnScrollListener(it) }
            scrollListener = object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount // кол-во элементов на экране
                    val totalItemCount = layoutManager.itemCount // сколько всего элементов в списке
                    val positionFirst =
                        layoutManager.findFirstVisibleItemPosition() // номер первого видимого элемента на экране

                    if (visibleItemCount + positionFirst >= totalItemCount && positionFirst >= 0) {
                        binding?.bottomProgressBar?.isVisible = true
                        viewModel.loadNextPage()
                    }
                }
            }.also {
                addOnScrollListener(it)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.vacanciesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacanciesState.Loading -> {
                    val a = binding?.vacanciesRecyclerView?.childCount ?: 0
                    if (a > 0) {
                        binding?.bottomProgressBar?.isVisible = true
                        Log.d(TAG, "observeViewModel: bottomProgressBar")
                    } else {
                        showLoading()
                    }
                    Log.d(TAG, "observeViewModel: showLoading()")
                    keyBoardVisibility(false)
                }

                is VacanciesState.Error -> {
                    hideCenteralProgressBar()
                    showError(state.responseState)
                    keyBoardVisibility(false)
                    Log.d(TAG, "observeViewModel: Error")
                }

                is VacanciesState.Success -> {
                    hideCenteralProgressBar()
                    updateRecyclerView(state.vacancies)
                    keyBoardVisibility(false)
                    binding?.bottomProgressBar?.isVisible = false
                    Log.d(TAG, "observeViewModel: Success")
                }

                is VacanciesState.Empty -> {
                    hideCenteralProgressBar()
                    showEmptyState()
                    keyBoardVisibility(false)
                    Log.d(TAG, "observeViewModel: Empty")
                }

                is VacanciesState.Hidden -> {
                    Log.d(TAG, "observeViewModel: Hidden")
                    clearRecyclerView()
                }
            }
        }
    }

    private fun clearRecyclerView() {
        updateRecyclerView(emptyList())
        showHiddenState()
    }

    private fun updateSearchIcon(isEmpty: Boolean) {
        binding?.clearSearchButton?.setImageResource(
            if (isEmpty) R.drawable.search_24px else R.drawable.close_24px
        )
    }

    private fun showHiddenState() {
        binding?.searchLayout?.visibility = View.VISIBLE
        binding?.errorLayout?.visibility = View.GONE
        binding?.noJobsLayout?.visibility = View.GONE
    }

    private fun showLoading() {
        Log.d(TAG, "observeViewModel: show central progressBar")
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.searchLayout?.visibility = View.GONE
        binding?.errorLayout?.visibility = View.GONE
        binding?.noJobsLayout?.visibility = View.GONE
        binding?.vacanciesRecyclerView?.visibility = View.GONE
    }

    private fun hideCenteralProgressBar() {
        binding?.progressBar?.visibility = View.GONE
    }

    private fun showError(responseState: ResponseStatusCode?) {
        when (responseState) {
            null -> {}
            ResponseStatusCode.ERROR -> {
                if (binding?.vacanciesRecyclerView?.isVisible == true) {
                    showResponseErrToast()
                } else {
                    binding?.searchLayout?.visibility = View.GONE
                    binding?.noJobsLayout?.visibility = View.GONE
                    binding?.vacanciesRecyclerView?.visibility = View.GONE
                    binding?.errorTv?.setText(R.string.server_error)
                    binding?.errorImage?.setImageResource(R.drawable.server_error_on_search_screen)
                }
            }

            ResponseStatusCode.NO_INTERNET -> {
                if (binding?.vacanciesRecyclerView?.isVisible == true) {
                    showNoInternetToast()
                } else {
                    binding?.searchLayout?.visibility = View.GONE
                    binding?.noJobsLayout?.visibility = View.GONE
                    binding?.vacanciesRecyclerView?.visibility = View.GONE
                    binding?.errorTv?.setText(R.string.no_internet)
                    binding?.errorImage?.setImageResource(R.drawable.no_internet_placeholder)
                }
            }

            else -> {}
        }
        binding?.errorLayout?.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        binding?.vacanciesRecyclerView?.visibility = View.GONE
        binding?.searchLayout?.visibility = View.GONE
        binding?.errorLayout?.visibility = View.GONE
        binding?.noJobsLayout?.visibility = View.VISIBLE

    }

    private fun updateRecyclerView(vacancies: List<Vacancy>) {
        binding?.vacanciesRecyclerView?.visibility = View.VISIBLE
        binding?.noJobsLayout?.visibility = View.GONE
        binding?.searchLayout?.visibility = View.GONE
        binding?.errorLayout?.visibility = View.GONE
        vacancyAdapter.submitList(vacancies)
        onItemClick?.let {
            vacancyAdapter.onItemClick = it
        }
    }

    private fun showNoInternetToast() {
        Toast.makeText(context, "Проверьте подключение к интернету", Toast.LENGTH_LONG)
            .show()
        binding?.bottomProgressBar?.isVisible = false
        Log.d(TAG, "showNoInternetToast: true")
    }

    private fun showResponseErrToast() {
        Toast.makeText(context, "Произошла ошибка", Toast.LENGTH_LONG)
            .show()
        binding?.bottomProgressBar?.isVisible = false
    }

    private fun keyBoardVisibility(visibile: Boolean) {
        val inputMethodManager =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        when (visibile) {
            true -> inputMethodManager?.showSoftInput(binding?.searchEditText, 0)
            else -> inputMethodManager?.hideSoftInputFromWindow(binding?.searchEditText?.windowToken, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        scrollListener = null
    }
}
