package ru.practicum.android.diploma.ui.custom

import android.content.Context
import android.renderscript.ScriptGroup.Binding
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.CustomRadioButtonBinding
import ru.practicum.android.diploma.databinding.FragmentFiltrationBinding
import ru.practicum.android.diploma.domain.models.entity.IndustryNested

class CustomRadioLayout @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attr, defStyleAttr)  {
    val binding: CustomRadioButtonBinding =  CustomRadioButtonBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        orientation = HORIZONTAL
        setOnClickListener{
            binding.radioButton.isChecked = !binding.radioButton.isChecked
        }
    }

    fun bind(industry: IndustryNested) {
        binding.industryName.text = industry.name
        binding.radioButton.tag = industry
    }

    fun setChecked(checked: Boolean) {
       binding.radioButton.isChecked = checked
    }

    fun isChecked() = binding.radioButton.isChecked

    fun setOnCheckedChangeListener(listener : CompoundButton.OnCheckedChangeListener) {
        binding.radioButton.setOnCheckedChangeListener(listener)
    }
}
