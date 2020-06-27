package com.egorovfond.dictionary

import android.app.Activity
import android.app.Application
import com.egorovfond.dictionary.di.DaggerAppComponent
import com.egorovfond.dictionary.di.koin.application
import com.egorovfond.dictionary.di.koin.mainScreen
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import javax.inject.Inject

class TranslatorApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TranslatorApp)
        }
    }
}