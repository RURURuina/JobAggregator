package ru.practicum.android.diploma.ui.filtration

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.bundle.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationBinding
import ru.practicum.android.diploma.presentation.filtration.FiltrationViewModel
import ru.practicum.android.diploma.ui.root.RootActivity

class FiltrationFragment : Fragment() {
    private val viewModel: FiltrationViewModel by viewModel()
    private var _binding: FragmentFiltrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFiltrationBinding.inflate(layoutInflater)
        return this.binding.root
    }

    override fun onDetach() {
        super.onDetach()
        navBarVisible(true)
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navBarVisible(false)
        prepareRegionButton()
        prepareIndustryButton()
        prepareBackBtn()
        prepareTextWatcher()
        prepareClearBtn()

    }

    private fun prepareIndustryButton() {
        binding.industryLayout.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_industryFragment)
        }
    }

    private fun prepareBackBtn() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun prepareClearBtn() {
        binding.clearSalaryButton.setOnClickListener {
            binding.etInputSalary.setText("")
        }
    }

    private fun prepareRegionButton() {
        binding.workPlaceLayout.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_selectRegionFragment)
        }

        binding.industryBtn.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_industryFragment)
        }
    }

    private fun navBarVisible(isVisible: Boolean) {
        (activity as RootActivity).bottomNavigationVisibility(isVisible)
    }

    private fun prepareTextWatcher() {
        binding.etInputSalary.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearSalaryButton.isVisible = s?.isBlank() != true
                binding.hintTitle.isActivated = s?.isBlank() != true
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

}
