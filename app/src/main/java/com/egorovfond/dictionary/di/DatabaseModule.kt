package com.egorovfond.dictionary.di

import com.egorovfond.dictionary.entities.IDatabase
import com.egorovfond.dictionary.entities.RetrofitDictionary
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    internal fun provideRepositoryRemote(): IDatabase =
        RetrofitDictionary()
}