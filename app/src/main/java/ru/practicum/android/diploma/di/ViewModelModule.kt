package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.details.DetailsFragmentViewModel
import ru.practicum.android.diploma.presentation.favorites.FavoriteJobViewModel
import ru.practicum.android.diploma.presentation.search.SearchJobViewModel
import ru.practicum.android.diploma.presentation.team.TeamViewModel
import ru.practicum.android.diploma.util.NetworkChecker

val viewModelModule = module {
    single { NetworkChecker(androidContext()) }
    viewModel {
        SearchJobViewModel(
            hhInteractor = get()
        )
    }
    viewModel {
        TeamViewModel()
    }
    viewModel {
        FavoriteJobViewModel(
            interactor = get()
        )
    }
    viewModel {
        DetailsFragmentViewModel(
            hhInteractor = get(),
            favoritesInteractor = get(),
            vacancySharingInteractor = get(),
            networkChecker = get()
        )
    }
}
