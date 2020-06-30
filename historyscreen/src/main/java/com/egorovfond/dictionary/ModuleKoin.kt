package com.egorovfond.dictionary



import com.egorovfond.dictionary.mvvm.HistoryInteractor
import com.egorovfond.dictionary.mvvm.HistoryViewModel
import com.egorovfond.dictionary.ui.HistoryActivity
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen = module {
    scope(named<HistoryActivity>()) {
        scoped { HistoryInteractor(get()) }
        viewModel { HistoryViewModel(get()) }
    }
}