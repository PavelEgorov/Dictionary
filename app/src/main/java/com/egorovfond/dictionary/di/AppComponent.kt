package com.egorovfond.dictionary.di

import android.app.Application
import com.egorovfond.dictionary.TranslatorApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
            InteractorModule::class,
            ViewModelModule::class,
            ActivityModule::class,
            DictionaryModule::class,
            DatabaseModule::class,
            AndroidSupportInjectionModule::class]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(englishVocabularyApp: TranslatorApp)
}