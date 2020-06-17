package com.egorovfond.dictionary.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.rx.SchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.*

abstract class BaseViewModel<T : List<SearchResult>>(
    protected open val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData(),
    protected open val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected open val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : ViewModel() {

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                )

    abstract fun findWorld(word: String)

    override fun onCleared() {
        compositeDisposable.clear()
        cancelJob()
    }

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
}