package ru.practicum.android.diploma.ui.filtration

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.presentation.filtration.FiltrationViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationBinding

class FiltrationFragment : Fragment() {
    private val viewModel: FiltrationViewModel by viewModels()
    private var binding: FragmentFiltrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFiltrationBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    // настройка листениров кнопок
   fun setupButtons() {
       binding?.indistryBtn?.setOnClickListener {
           findNavController().navigate(R.id.action_filtrationFragment_to_industryFragment)
       }
   }
}
