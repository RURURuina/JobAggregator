package ru.practicum.android.diploma.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.convertors.VacancyDtoConvertor
import ru.practicum.android.diploma.data.network.HhApiService
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.repository.HhRepositoryImpl
import ru.practicum.android.diploma.domain.api.HhInteractor
import ru.practicum.android.diploma.domain.api.HhRepository
import ru.practicum.android.diploma.domain.impl.HhInteractorImpl
import ru.practicum.android.diploma.ui.search.SearchJobViewModel

var searchJobViewModule = module {
    viewModel<SearchJobViewModel> {
        println("SearchJobViewModel")
        SearchJobViewModel(hhInteractor = get())
    }
    single<HhInteractor> {
        println("HhInteractor")
        HhInteractorImpl(
            hhRepository = get()
        )
    }

    single<HhRepository> {
        println("HhRepository")
        HhRepositoryImpl(
            networkClient = get(),
            vacancyDtoConvertor = get()
        )
    }
    single<VacancyDtoConvertor> {
        println("VacancyDtoConvertor")
        VacancyDtoConvertor()
    }

    single<NetworkClient> {
        println("NetworkClient")

        RetrofitNetworkClient(
            connectService = get(),
            context = androidContext()
        )
    }

    single<HhApiService> {
        println("HhApiService")
        val baseUrl = "https://api.hh.ru/"

        val interceptorHttp = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptorHttp)
            .build()

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HhApiService::class.java)
    }
}
