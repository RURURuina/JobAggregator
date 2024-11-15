package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.HhInteractor
import ru.practicum.android.diploma.domain.impl.HhInteractorImpl

val interactorModule = module {
    single<HhInteractor> {
        HhInteractorImpl(
            hhRepository = get()
        )
    }
}
