package ru.practicum.android.diploma.ui.industry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.Editable
import android.text.TextWatcher
import android.widget.RadioButton
import android.widget.RadioGroup
import ru.practicum.android.diploma.presentation.industry.IndustryViewModel
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.domain.models.entity.IndustryNested
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.ui.custom.CustomRadioLayout

class IndustryFragment : Fragment() {

    private var _binding: FragmentIndustryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: IndustryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
        setupSearchFilter()
    }

    private fun setupUI() {
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.clearSearchButton.setOnClickListener {
            binding.filterEditText.text.clear()
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            val industry = radioButton.tag as? IndustryNested
            industry?.let {
                viewModel.setSelectedIndustry(it)
                binding.selectButton.visibility = View.VISIBLE
            }
        }
    }

    private fun setupObservers() {
        viewModel.industries.observe(viewLifecycleOwner) { industries ->
            updateRadioGroup(industries)
        }
    }

    private fun setupSearchFilter() {
        binding.filterEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filterIndustries(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateRadioGroup(industries: List<IndustryNested>) {
        binding.radioGroup.removeAllViews()

        // Список для хранения всех CustomRadioLayout
        val radioLayouts = mutableListOf<CustomRadioLayout>()

        industries.forEach { industry ->
            val customRadioLayout = CustomRadioLayout(requireContext()).apply {
                bind(industry)
                layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                )

                // Обработчик изменения состояния для каждого CustomRadioLayout
                setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        // Отключаем все остальные RadioButton
                        radioLayouts.forEach { layout ->
                            if (layout != this) {
                                layout.setChecked(false)
                            }
                        }
                        // Обновляем UI
                        val industry = buttonView.tag as? IndustryNested
                        industry?.let {
                            viewModel.setSelectedIndustry(it)
                            binding.selectButton.visibility = View.VISIBLE
                        }
                    }
                }
            }

            radioLayouts.add(customRadioLayout)
            binding.radioGroup.addView(customRadioLayout)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
