package com.egorovfond.dictionary

import androidx.room.Room
import com.egorovfond.dictionary.entities.IDatabase
import com.egorovfond.dictionary.entities.RetrofitDictionary
import com.egorovfond.dictionary.mvvm.HistoryInteractor
import com.egorovfond.dictionary.mvvm.HistoryViewModel
import com.egorovfond.dictionary.mvvm.MainInteractor
import com.egorovfond.dictionary.mvvm.MainViewModel
import com.egorovfond.dictionary.room.HistoryDataBase
import com.egorovfond.dictionary.usecases.DictionaryModel
import com.egorovfond.dictionary.usecases.IDictionary
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen = module {
    factory { com.egorovfond.dictionary.mvvm.HistoryViewModel(get()) }
    factory { com.egorovfond.dictionary.mvvm.HistoryInteractor(get()) }
}