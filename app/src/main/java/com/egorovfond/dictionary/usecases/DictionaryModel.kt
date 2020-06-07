package com.egorovfond.dictionary.usecases

import com.egorovfond.dictionary.entities.IDatabase
import io.reactivex.rxjava3.schedulers.Schedulers

class DictionaryModel(val database : IDatabase): IDictionary {
    override fun getAll()= database.getAll().subscribeOn(Schedulers.io())

    override fun getByText(text: String) =  database.getByText(text).subscribeOn(Schedulers.io())
}