package com.egorovfond.dictionary.usecases

import com.egorovfond.dictionary.entities.IDatabase
import io.reactivex.rxjava3.schedulers.Schedulers

class DictionaryModel @Inject constructor(val database : IDatabase): IDictionary {
    override suspend fun getByText(text: String): List<SearchResult> {
        return database.getByText(text)
    }
}