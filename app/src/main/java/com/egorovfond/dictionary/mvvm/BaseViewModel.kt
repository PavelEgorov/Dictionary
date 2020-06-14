package com.egorovfond.dictionary.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.rx.SchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel<T : List<SearchResult>>(
    protected open val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData(),
    protected open val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected open val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : ViewModel() {

    abstract fun findWorld(word: String)

    override fun onCleared() {
        compositeDisposable.clear()
    }
}