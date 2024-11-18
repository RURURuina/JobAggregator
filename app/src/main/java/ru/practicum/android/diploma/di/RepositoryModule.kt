package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.convertors.VacancyDtoConvertor
import ru.practicum.android.diploma.data.repository.HhRepositoryImpl
import ru.practicum.android.diploma.data.repository.VacancySharingRepositoryImpl
import ru.practicum.android.diploma.domain.api.hh.HhRepository
import ru.practicum.android.diploma.domain.api.sharing.VacancySharingRepository

val repositoryModule = module {
    single<VacancyDtoConvertor> {
        VacancyDtoConvertor()
    }

    single<HhRepository> {
        HhRepositoryImpl(
            networkClient = get(),
            vacancyDtoConvertor = get()
        )
    }

    single<VacancySharingRepository> {
        VacancySharingRepositoryImpl(
            context = androidContext()
        )
    }
}
