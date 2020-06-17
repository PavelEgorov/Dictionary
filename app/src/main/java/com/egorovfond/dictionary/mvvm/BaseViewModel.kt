package com.egorovfond.dictionary.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.rx.SchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
<<<<<<< HEAD
import kotlinx.coroutines.*
=======
>>>>>>> origin/master

abstract class BaseViewModel<T : List<SearchResult>>(
    protected open val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData(),
    protected open val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected open val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : ViewModel() {

<<<<<<< HEAD
    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                )

=======
>>>>>>> origin/master
    abstract fun findWorld(word: String)

    override fun onCleared() {
        compositeDisposable.clear()
<<<<<<< HEAD
        cancelJob()
    }

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
=======
>>>>>>> origin/master
    }
}