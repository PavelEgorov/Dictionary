package com.egorovfond.dictionary.mvvm

import io.reactivex.rxjava3.core.Single

interface Interactor<T> {
    suspend fun getData(word: String): T
}