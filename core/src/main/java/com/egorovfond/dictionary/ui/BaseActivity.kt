package com.egorovfond.dictionary.ui

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.egorovfond.core.R
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.mvvm.BaseViewModel
import com.egorovfond.dictionary.mvvm.Interactor
import com.egorovfond.utils.network.OnlineLiveData

const val HISTORY_ACTIVITY_FEATURE_NAME = "historyScreen"
const val HISTORY_ACTIVITY_PATH = "com.egorovfond.dictionary.ui.HistoryActivity"

abstract class BaseActivity<T : List<SearchResult>, I : Interactor<T>> : AppCompatActivity() {
    protected var isNetworkAvailable: Boolean = true

    abstract val model: BaseViewModel<T>
    abstract fun renderData()

    private fun subscribeToNetworkChange() {
        OnlineLiveData(this).observe(
            this@BaseActivity,
            Observer<Boolean> {
                isNetworkAvailable = it
                if (!isNetworkAvailable) {
                    Toast.makeText(
                        this@BaseActivity,
                        R.string.dialog_message_device_is_offline,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}