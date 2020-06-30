package com.egorovfond.dictionary.di.koin

import androidx.room.Room
import com.egorovfond.dictionary.entities.IDatabase
import com.egorovfond.dictionary.entities.RetrofitDictionary
import com.egorovfond.dictionary.mvvm.HistoryInteractor
import com.egorovfond.dictionary.mvvm.HistoryViewModel
import com.egorovfond.dictionary.mvvm.MainInteractor
import com.egorovfond.dictionary.mvvm.MainViewModel
import com.egorovfond.dictionary.room.HistoryDataBase
import com.egorovfond.dictionary.ui.MainActivity
import com.egorovfond.dictionary.usecases.DictionaryModel
import com.egorovfond.dictionary.usecases.IDictionary
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen))
}

val application = module{
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<IDatabase> { RetrofitDictionary() }
    single<IDictionary>{ DictionaryModel(get<IDatabase>()) }
}

val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped { MainInteractor(get()) }
        viewModel { MainViewModel(get()) }
    }
}

//val historyScreen = module {
//    factory { com.egorovfond.dictionary.mvvm.HistoryViewModel(get()) }
//    factory { com.egorovfond.dictionary.mvvm.HistoryInteractor(get()) }
//}