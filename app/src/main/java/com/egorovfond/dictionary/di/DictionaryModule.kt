package com.egorovfond.dictionary.di

import com.egorovfond.dictionary.entities.IDatabase
import com.egorovfond.dictionary.usecases.DictionaryModel
import com.egorovfond.dictionary.usecases.IDictionary
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DictionaryModule {
    @Provides
    @Singleton
    internal fun provide(database: IDatabase): IDictionary =
        DictionaryModel(database)
}