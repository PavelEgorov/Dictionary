package com.egorovfond.dictionary.usecases

import com.egorovfond.dictionary.entities.IDatabase
import com.egorovfond.dictionary.entities.data.SearchResult
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DictionaryModel @Inject constructor(val database : IDatabase): IDictionary {
    override suspend fun getByText(text: String): List<SearchResult> {
        return database.getByText(text)
    }
}