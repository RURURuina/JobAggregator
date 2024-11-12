package ru.practicum.android.diploma.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSearchJobBinding

class SearchJobFragment : Fragment() {
    private var binding: FragmentSearchJobBinding? = null
    private val viewModel: SearchJobViewModel by viewModel()

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
        observeViewModel()
        viewModel.start()

    }

    // эта функция следит за переменными во ViewModel
    fun observeViewModel() {
        viewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            vacancies.map { Log.d("viewModel", "${it.name}") }

        }
    }
}
