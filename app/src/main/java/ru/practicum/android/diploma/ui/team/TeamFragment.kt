package ru.practicum.android.diploma.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentTeamBinding
import ru.practicum.android.diploma.presentation.team.TeamViewModel

class TeamFragment : Fragment() {
    private var binding: FragmentTeamBinding? = null
    private val viewModel: TeamViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTeamBinding.inflate(layoutInflater)
        return binding?.root
    }
}
