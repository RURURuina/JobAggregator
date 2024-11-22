package ru.practicum.android.diploma.ui.filtration


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filtration.FiltrationViewModel

class FiltrationFragment : Fragment() {

    companion object {
        fun newInstance() = FiltrationFragment()
    }

    private val viewModel: FiltrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_filtration, container, false)
    }
}
