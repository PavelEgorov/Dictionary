package com.egorovfond.dictionary.mvvm

import io.reactivex.rxjava3.core.Single

interface Interactor<T> {
    fun getData(word: String): Single<T>
}