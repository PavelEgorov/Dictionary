package com.egorovfond.dictionary.di.koin

import com.egorovfond.dictionary.entities.IDatabase
import com.egorovfond.dictionary.entities.RetrofitDictionary
import com.egorovfond.dictionary.entities.data.Dictionary
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.mvvm.MainInteractor
import com.egorovfond.dictionary.mvvm.MainViewModel
import com.egorovfond.dictionary.usecases.DictionaryModel
import com.egorovfond.dictionary.usecases.IDictionary
import org.koin.dsl.module

val application = module{
    single<IDatabase> { RetrofitDictionary() }
    single<IDictionary>{ DictionaryModel(get<IDatabase>()) }
}

val mainScreen = module {
    factory { MainInteractor(get()) }
    factory { MainViewModel(get()) }
}