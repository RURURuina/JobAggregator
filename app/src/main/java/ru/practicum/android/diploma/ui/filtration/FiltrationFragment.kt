package ru.practicum.android.diploma.ui.filtration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navBarVisible(false)

        // Загрузка сохраненного фильтра
        viewModel.loadSavedFilter()

        setupObservers()
        setupListeners()
        prepareButtons()

    }

    private fun setupObservers() {
        viewModel.filterState.observe(viewLifecycleOwner) { filter ->
            filter?.let {
                // Заполнение полей сохраненными значениями
                if(it.regionName != null && it.countryName != null)  {
                    binding.regionText.text = it.countryName + "," + it.regionName
                }
                binding.industryText.setText(it.industryName ?: "")
                binding.inputSalary.setText(it.salary ?: "")
                binding.checkBox.isChecked = it.onlySalaryFlag ?: false
            }
            setApplyResetButtonsVis(false)
        }
    }

    private fun setApplyResetButtonsVis(vis: Boolean){
        if(vis){
            binding.applyButton.visibility = View.VISIBLE
            binding.resetText.visibility = View.VISIBLE
        } else{
            binding.applyButton.visibility = View.GONE
            binding.resetText.visibility = View.GONE
        }
    }

    private fun setupListeners() {
        // Слушатель изменения зарплаты
        binding.inputSalary.doAfterTextChanged { text ->
            setApplyResetButtonsVis(true)
        }

        // Слушатель чекбокса
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            setApplyResetButtonsVis(true)
        }

        // Кнопка назад
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        //Кнопка применить
        binding.applyButton.setOnClickListener{
            val filter = viewModel.createFilterFromUI(
                salary = binding.inputSalary.text.toString(),
                onlySalaryFlag = binding.checkBox.isChecked
            )
            viewModel.saveFilter(filter)
        }

        //Кнопка сбросить
        binding.resetText.setOnClickListener{
            val filter = viewModel.createFilterFromUI(
                salary = "",
                onlySalaryFlag = false
            )
            viewModel.saveFilter(filter)
            viewModel.loadSavedFilter()
        }
    }

    private fun prepareButtons() {
        // Кнопки выбора региона и отрасли
        binding.workPlaceLayout.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_selectRegionFragment)
        }

        binding.industryLayout.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_industryFragment)
        }
    }

    private fun navBarVisible(isVisible: Boolean) {
        (activity as RootActivity).bottomNavigationVisibility(isVisible)
    }

    override fun onDetach() {
        super.onDetach()
        navBarVisible(true)
        _binding = null
    }
}
