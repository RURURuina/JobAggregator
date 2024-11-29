package ru.practicum.android.diploma.ui.region

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.bundle.Bundle
import androidx.core.bundle.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSelectRegionBinding
import ru.practicum.android.diploma.presentation.region.SelectRegionViewModel
import ru.practicum.android.diploma.ui.region.model.SelectRegionFragmentState
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.ui.root.RootActivity.Companion.COUNTRY_TRANSFER_KEY

class SelectRegionFragment : Fragment() {
    private val viewModel: SelectRegionViewModel by viewModel()
    private var _binding: FragmentSelectRegionBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSelectRegionBinding.inflate(layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navBarVisible(false)
        prepareButtons()
        observeVieModel()
        viewModel.getFilter()
    }

    private fun fillAreaLayout(areaName: String?, countryId: String?) {
        val bundle = bundleOf(COUNTRY_TRANSFER_KEY to countryId.toString())
        binding.area.text = areaName
        binding.areaTitle.isVisible = areaName != null
        binding.areaButton.setImageResource(
            if (binding.areaTitle.isVisible) R.drawable.close_24px else R.drawable.arrow_forward_24px
        )
        binding.areaButton.setOnClickListener {
            if (binding.areaTitle.isVisible) {
                binding.areaButton.setImageResource(
                    R.drawable.arrow_forward_24px
                )
                binding.areaTitle.isVisible = false
                binding.area.text = null
                // то что сохранит во вьюмодели
                viewModel.clearArea()
            }
            binding.selectButton.isVisible = true
        }
        binding.regionLayout.setOnClickListener {
            findNavController().navigate(R.id.action_selectRegionFragment_to_citySelectFragment, bundle)
        }
    }

    private fun observeVieModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SelectRegionFragmentState.Content -> {
                    fillCountryLayout(state.countryName)
                    fillAreaLayout(state.areaName, state.countryId)
                }

                SelectRegionFragmentState.Exit -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun fillCountryLayout(countryName: String?) {
        binding.country.text = countryName
        binding.countryTitle.isVisible = countryName != null
        binding.countryBtn.setImageResource(
            if (binding.countryTitle.isVisible) R.drawable.close_24px else R.drawable.arrow_forward_24px
        )
        binding.countryBtn.setOnClickListener {
            if (binding.countryTitle.isVisible) {
                binding.countryBtn.setImageResource(
                    R.drawable.arrow_forward_24px
                )
                binding.countryTitle.isVisible = false
                binding.country.text = null
                // то что сохранит во вьюмодели
                viewModel.clearCountry()
            } else {
                findNavController().navigate(R.id.action_selectRegionFragment_to_selectCountryFragment)
            }
            binding.selectButton.isVisible = true
        }
    }

    private fun prepareButtons() {
        binding.regionLayout.setOnClickListener {
            findNavController().navigate(R.id.action_selectRegionFragment_to_citySelectFragment)
        }
        binding.countryLayout.setOnClickListener {
            findNavController().navigate(R.id.action_selectRegionFragment_to_selectCountryFragment)
        }

        binding.backLay.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.selectButton.setOnClickListener {
            viewModel.saveExit()
        }
    }

    override fun onDetach() {
        super.onDetach()
        navBarVisible(false)
        _binding = null
    }

    private fun navBarVisible(isVisible: Boolean) {
        (activity as RootActivity).bottomNavigationVisibility(isVisible)
    }
}
