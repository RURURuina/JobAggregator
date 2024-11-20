package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFavoriteJobBinding
import ru.practicum.android.diploma.presentation.favorites.FavoriteJobViewModel

class FavoriteJobFragment : Fragment() {
    private var binding: FragmentFavoriteJobBinding? = null
    private val viewModel: FavoriteJobViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        println()
        binding = FragmentFavoriteJobBinding.inflate(layoutInflater)
        return binding?.root
    }
}
