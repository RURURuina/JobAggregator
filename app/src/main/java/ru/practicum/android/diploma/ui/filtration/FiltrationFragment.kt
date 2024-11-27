package ru.practicum.android.diploma.ui.filtration

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navBarVisible(false)

        // Загрузка сохраненного фильтра
        viewModel.loadSavedFilter()

        setupObservers()
        prepareTextWatcher()
        setupListeners()
        prepareButtons()

    }

    private fun setupObservers() {
        viewModel.filterState.observe(viewLifecycleOwner) { filter ->
            filter?.let {
                // Заполнение полей сохраненными значениями
                if (it.regionName != null && it.countryName != null) {
                    binding.workPlace.text = it.countryName + "," + it.regionName
                }
                binding.industry.setText(it.industryName ?: "")
                binding.etInputSalary.setText(it.salary ?: "")
                binding.checkBox.isChecked = it.onlySalaryFlag ?: false
            }
            setApplyResetButtonsVis(false)
        }
    }

    private fun setApplyResetButtonsVis(vis: Boolean) {
        if (vis) {
            binding.submitButton.visibility = View.VISIBLE
            binding.resetButton.visibility = View.VISIBLE
        } else {
            binding.submitButton.visibility = View.GONE
            binding.resetButton.visibility = View.GONE
        }
    }

    private fun setupListeners() {
        // Слушатель изменения зарплаты
        binding.etInputSalary.doAfterTextChanged { text ->
            setApplyResetButtonsVis(true)
        }

        // Слушатель чекбокса
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            setApplyResetButtonsVis(true)
        }
        // Кнопка очистить
        binding.clearSalaryButton.setOnClickListener {
            binding.etInputSalary.setText("")
        }

        // Кнопка назад
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        // Кнопка применить
        binding.submitButton.setOnClickListener {
            val filter = viewModel.createFilterFromUI(
                salary = binding.etInputSalary.text.toString(),
                onlySalaryFlag = binding.checkBox.isChecked
            )
            viewModel.saveFilter(filter)
        }

        // Кнопка сбросить
        binding.resetButton.setOnClickListener {
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

    private fun prepareTextWatcher() {
        binding.etInputSalary.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Не используется
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearSalaryButton.isVisible = s?.isBlank() != true
                binding.hintTitle.isActivated = s?.isBlank() != true
            }

            override fun afterTextChanged(s: Editable?) {
                // Не используется
            }
        })
    }

}
