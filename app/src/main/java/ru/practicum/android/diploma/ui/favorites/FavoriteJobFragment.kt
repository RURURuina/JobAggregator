package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteJobBinding
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.presentation.favorites.FavoriteJobViewModel
import ru.practicum.android.diploma.ui.favorites.models.FavoritesState
import ru.practicum.android.diploma.ui.search.adapters.VacancyAdapter

class FavoriteJobFragment : Fragment() {
    private var binding: FragmentFavoriteJobBinding? = null
    private val viewModel: FavoriteJobViewModel by viewModel()
    private var vacancyAdapter: VacancyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        println()
        binding = FragmentFavoriteJobBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        viewModel.getVacancies()
        viewModel.favoritesState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vacancyAdapter = null
        binding?.favoritesRv?.adapter = null
    }

    private fun init() {
        // добавлять слушателя кликов на адаптер когда будет

        vacancyAdapter = VacancyAdapter()
        binding?.favoritesRv?.adapter = vacancyAdapter
    }

    private fun render(state: FavoritesState) {
        when (state) {
            is FavoritesState.Content -> showFavorites(state.favoritesVacancies)
            is FavoritesState.Empty -> showPlaceholder(state.message)
            is FavoritesState.Loading -> showLoading()
            is FavoritesState.Error -> showPlaceholder(state.message)
        }
    }

    private fun showFavorites(favoriteList: List<Vacancy>) {
        binding?.progressBar?.visibility = View.GONE
        binding?.favoritesListPlaceholder?.visibility = View.GONE
        binding?.favoritesRv?.visibility = View.VISIBLE
        vacancyAdapter?.submitList(favoriteList)
    }

    private fun showLoading() {
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.favoritesRv?.visibility = View.GONE
        binding?.favoritesListPlaceholder?.visibility = View.GONE
    }

    private fun showPlaceholder(@StringRes message: Int) {
        binding?.progressBar?.visibility = View.GONE
        binding?.favoritesRv?.visibility = View.GONE

        val drawableRes = when (message) {
            R.string.empty_list -> R.drawable.empty_favorites_list_placeholder
            else -> R.drawable.nothing_found_placeholder
        }

        binding?.favoritesListPlaceholderImage?.setImageResource(drawableRes)
        binding?.favoritesListPlaceholderText?.text = getString(message)
        binding?.favoritesListPlaceholder?.visibility = View.VISIBLE
    }
}
