package com.egorovfond.dictionary.di

import com.egorovfond.dictionary.mvvm.MainInteractor
import com.egorovfond.dictionary.usecases.IDictionary
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        repositoryRemote: IDictionary
    ) = MainInteractor(repositoryRemote)
}