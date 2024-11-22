package ru.practicum.android.diploma.ui.details

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
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
    private var vacancyId: String? = null
    private var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onDetach() {
        super.onDetach()
        true.navBarVisible()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        false.navBarVisible()
        prepareViewModel()
        getVacancyId()
        initViewModel()
        prepareBackButton()
        prepareLikeButton()
        prepareShareButton()

        viewModel.isFavoriteLiveData.observe(viewLifecycleOwner) { isFavorite ->
            binding?.toolbarLikeButton?.isSelected = isFavorite
        }
    }

    private fun prepareShareButton() {
        binding?.toolbarShareButton?.setOnClickListener {
            viewModel.shareVacancy()
        }
    }

    private fun prepareLikeButton() {
        binding?.toolbarLikeButton?.setOnClickListener {
            viewModel.likeButton()
        }
    }

    private fun initViewModel() {
        vacancyId?.let {
            viewModel.start(it)
            true.progressBarVisible()
        }
    }

    private fun prepareBackButton() {
        binding?.preview?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun render(state: DetailsFragmentState) {
        when (state) {
            is DetailsFragmentState.Content -> {
                showContent(state.vacancy)
                binding?.errorServer?.isVisible = false
                binding?.errorLayout?.isVisible = false
            }

            is DetailsFragmentState.ERROR -> {
                renderError(state.errState)
            }
        }
    }

    private fun renderError(errState: ResponseStatusCode?) {
        when (errState) {
            ResponseStatusCode.ERROR -> {
                binding?.errorServer?.isVisible = true
            }

            ResponseStatusCode.NO_INTERNET -> {
                binding?.errorLayout?.isVisible = true
            }

            ResponseStatusCode.OK -> {
                /*наследие sealed classa*/
            }

            else -> {
                binding?.errorServer?.isVisible = true
            }
        }
    }

    private fun showContent(vacancy: Vacancy) {
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
            binding?.keySkillsTitle?.isVisible = keySkills.isNotEmpty()
            var str = ""
            keySkills.map { str += "<ul> <li>${it.name}.</li>" }
            binding?.keySkillsText?.text = Html.fromHtml(
                str,
                Html.FROM_HTML_MODE_COMPACT
            )
        }
    }

    private fun fillEmployer(vacancy: Vacancy) {
        binding?.cardTitleText?.text = vacancy.employer?.name
        binding?.cardCityText?.text = vacancy.adress?.full ?: vacancy.area?.name

//        этот блок для отображения заглушки без интернета
        context?.let { context ->
            binding?.cardImage?.fillBy(vacancy.employer?.logoUrls?.original, context)
        }
    }

    private fun fillEmployment(vacancy: Vacancy?) {
        val str = "${vacancy?.employment?.name}. ${vacancy?.schedule?.name}"
        binding?.workShiftText?.text = str
    }

    private fun fillExperience(experience: Experience?) {
        binding?.experienceTitleText?.text = experience?.name
    }

    private fun fillTitle(tittle: String?) {
        binding?.titleName?.text = tittle
    }

    private fun fillDescription(description: String?) {
        binding?.descriptionHtmlText?.text =
            Html.fromHtml(
                description,
                Html.FROM_HTML_MODE_COMPACT
            )
    }

    private fun fillSalary(salary: Salary?) {
        binding?.titleSalary?.text = salary.format()
    }

    private fun prepareViewModel() {
        viewModel.observeState().observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun getVacancyId() {
        vacancyId = arguments?.getString(VACANCY_TRANSFER_KEY)
    }

    private fun Boolean.navBarVisible() {
        (activity as RootActivity).bottomNavigationVisibility(this)
    }

    private fun Boolean.progressBarVisible() {
        binding?.progressBar?.isVisible = this
    }

}
