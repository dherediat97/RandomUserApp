package com.dherediat97.randomuserapp

import android.app.Application
import com.dherediat97.randomuserapp.di.repositoriesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RandomUserApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RandomUserApp)
            modules(repositoriesModule)
        }
    }
}