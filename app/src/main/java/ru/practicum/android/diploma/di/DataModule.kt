package ru.practicum.android.diploma.di

import androidx.room.Room
import com.google.gson.Gson
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.network.HhApiService
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient

val dataModule = module {
    factory { Gson() }

    single<HhApiService> {
        val baseUrl = "https://api.hh.ru/"

        val headers = Headers
            .Builder()
            .add("Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}")
            .add("HH-User-Agent: YpDiplomaProject/1.0 (4habibulin@gmail.com)").build()

        val interceptorHttp = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptorHttp)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request = chain.request().newBuilder().headers(headers).build()
                chain.proceed(request)
            })
            .build()

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HhApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(
            hhService = get(),
            context = androidContext()
        )
    }
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDatabase::class.java,
            name = "appDataBase.db"
        ).build()
    }
    single { get<AppDatabase>().favoritesVacancyDao() }
}
