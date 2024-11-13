package ru.practicum.android.diploma.util

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.dataModule
import ru.practicum.android.diploma.di.interactorModule
import ru.practicum.android.diploma.di.repositoryModule
import ru.practicum.android.diploma.di.searchJobViewModule
import ru.practicum.android.diploma.di.viewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                repositoryModule,
                interactorModule,
                dataModule,
                viewModelModule,
                searchJobViewModule
            )
        }

        setTheme()
    }

    private fun setTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}
