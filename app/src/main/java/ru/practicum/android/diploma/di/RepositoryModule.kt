package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.convertors.VacancyDtoConvertor
import ru.practicum.android.diploma.data.repository.HhRepositoryImpl
import ru.practicum.android.diploma.domain.api.HhRepository

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
}
