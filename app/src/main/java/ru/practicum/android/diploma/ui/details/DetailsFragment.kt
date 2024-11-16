package ru.practicum.android.diploma.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.details.DetailsFragmentViewModel
import ru.practicum.android.diploma.ui.details.models.DetailsFragmentState
import ru.practicum.android.diploma.ui.root.RootActivity.Companion.VACANCY_TRANSFER_KEY

class DetailsFragment : Fragment() {
    private val viewModel: DetailsFragmentViewModel by viewModel()
    private var vacancyId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
        getVacancyId()
        vacancyId?.let {
            viewModel.start(it)
        }
    }

    private fun render(state: DetailsFragmentState) {
        when (state) {
            else -> {}
        }
    }

    private fun prepareViewModel() {
        viewModel.observeState().observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun getVacancyId() {
        vacancyId = arguments?.getString(VACANCY_TRANSFER_KEY)
        Log.d("DetailsFragment getVacancyId", "$vacancyId")
    }
}
