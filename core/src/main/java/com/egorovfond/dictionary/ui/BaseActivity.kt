package com.egorovfond.dictionary.ui

import androidx.appcompat.app.AppCompatActivity
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.mvvm.BaseViewModel
import com.egorovfond.dictionary.mvvm.Interactor
const val HISTORY_ACTIVITY_FEATURE_NAME = "historyScreen"
const val HISTORY_ACTIVITY_PATH = "com.egorovfond.dictionary.ui.HistoryActivity"

abstract class BaseActivity<T : List<SearchResult>, I : Interactor<T>> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>
    abstract fun renderData()
}