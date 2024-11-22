package ru.practicum.android.diploma.ui.country

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.bundle.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.country.SelectCountryViewModel

class SelectCountryFragment : Fragment() {

    companion object {
        fun newInstance() = SelectCountryFragment()
    }

     val viewModel by viewModels<SelectCountryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_select_country, container, false)
    }
}
