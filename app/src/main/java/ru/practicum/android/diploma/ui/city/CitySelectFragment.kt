package ru.practicum.android.diploma.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.city.CitySelectViewModel

class CitySelectFragment : Fragment() {

    companion object {
        fun newInstance() = CitySelectFragment()
    }

    private val viewModel: CitySelectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_city_select, container, false)
    }
}
