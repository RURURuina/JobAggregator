package ru.practicum.android.diploma.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.bundle.Bundle
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.domain.models.entity.Experience
import ru.practicum.android.diploma.domain.models.entity.KeySkill
import ru.practicum.android.diploma.domain.models.entity.Salary
import ru.practicum.android.diploma.domain.models.entity.Vacancy
import ru.practicum.android.diploma.presentation.details.DetailsFragmentViewModel
import ru.practicum.android.diploma.ui.details.models.DetailsFragmentState
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.ui.root.RootActivity.Companion.VACANCY_TRANSFER_KEY
import ru.practicum.android.diploma.util.ResponseStatusCode
import ru.practicum.android.diploma.util.fillBy
import ru.practicum.android.diploma.util.format

class DetailsFragment : Fragment() {
    private val viewModel: DetailsFragmentViewModel by viewModel()
    private var _binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        return _binding?.root
    }

    override fun onDetach() {
        super.onDetach()
        true.navBarVisible()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        false.navBarVisible()
        prepareViewModel()
        initViewModel()
        prepareBackButton()
        prepareLikeButton()
        prepareShareButton()

        viewModel.isFavoriteLiveData.observe(viewLifecycleOwner) { isFavorite ->
            _binding?.toolbarLikeButton?.isSelected = isFavorite
        }
    }

    private fun prepareShareButton() {
        _binding?.toolbarShareButton?.setOnClickListener {
            viewModel.shareVacancy()
        }
    }

    private fun prepareLikeButton() {
        _binding?.toolbarLikeButton?.setOnClickListener {
            viewModel.likeButton()
        }
    }

    private fun initViewModel() {
        viewModel.getVacancy(arguments?.getString(VACANCY_TRANSFER_KEY))
        true.progressBarVisible()
    }

    private fun prepareBackButton() {
        _binding?.preview?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun render(state: DetailsFragmentState) {
        when (state) {
            is DetailsFragmentState.Content -> {
                showContent(state.vacancy)
                _binding?.errorServer?.isVisible = false
                _binding?.errorLayout?.isVisible = false
            }

            is DetailsFragmentState.ERROR -> {
                false.progressBarVisible()
                renderError(state.errState)
            }
        }
    }

    private fun renderError(errState: ResponseStatusCode?) {
        when (errState) {
            ResponseStatusCode.Error -> {
                _binding?.errorServer?.isVisible = true
                _binding?.content?.isVisible = false
            }

            ResponseStatusCode.NoInternet -> {
                _binding?.connectionErrorLayout?.isVisible = true
                _binding?.content?.isVisible = false
            }

            ResponseStatusCode.Ok -> {
                _binding?.errorLayout?.isVisible = true
                _binding?.content?.isVisible = false
            }

            else -> {
                _binding?.errorServer?.isVisible = true
                _binding?.content?.isVisible = false
            }
        }
    }

    private fun showContent(vacancy: Vacancy) {
        _binding?.content?.isVisible = true
        fillSalary(vacancy.salary)
        fillDescription(vacancy.description)
        fillTitle(vacancy.name)
        fillExperience(vacancy.experience)
        fillEmployment(vacancy)
        fillEmployer(vacancy)
        fillKeySkills(vacancy.keySkills)
        false.progressBarVisible()
    }

    private fun fillKeySkills(keySkills: List<KeySkill>?) {
        keySkills?.let {
            _binding?.keySkillsTitle?.isVisible = keySkills.isNotEmpty()
            var str = ""
            keySkills.map { str += getString(R.string.key_skill_mask, it.name) }
            _binding?.keySkillsText?.text = HtmlCompat.fromHtml(
                str,
                HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
            )
        }
    }

    private fun fillEmployer(vacancy: Vacancy) {
        _binding?.cardTitleText?.text = vacancy.employer?.name
        _binding?.cardCityText?.text = vacancy.adress?.full ?: vacancy.area?.name

//        этот блок для отображения заглушки без интернета
        context?.let { context ->
            _binding?.cardImage?.fillBy(vacancy.employer?.logoUrls?.original, context)
        }
    }

    private fun fillEmployment(vacancy: Vacancy?) {
        val str = "${vacancy?.employment?.name}. ${vacancy?.schedule?.name}"
        _binding?.workShiftText?.text = str
    }

    private fun fillExperience(experience: Experience?) {
        _binding?.experienceTitleText?.text = experience?.name
    }

    private fun fillTitle(tittle: String?) {
        _binding?.titleName?.text = tittle
    }

    private fun fillDescription(description: String?) {
        description?.let {
            _binding?.descriptionHtmlText?.text = HtmlCompat.fromHtml(
                it,
                HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
            )
        }
    }

    private fun fillSalary(salary: Salary?) {
        _binding?.titleSalary?.text = salary.format()
    }

    private fun prepareViewModel() {
        viewModel.observeState().observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun Boolean.navBarVisible() {
        (activity as RootActivity).bottomNavigationVisibility(this)
    }

    private fun Boolean.progressBarVisible() {
        _binding?.progressBar?.isVisible = this
        _binding?.content?.isVisible = !this
    }

}
