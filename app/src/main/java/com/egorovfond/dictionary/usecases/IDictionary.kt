package com.egorovfond.dictionary.usecases

import com.egorovfond.dictionary.entities.data.Dictionary
import com.egorovfond.dictionary.entities.data.SearchResult
import io.reactivex.rxjava3.core.Single

interface IDictionary {
    fun getAll() : Single<List<SearchResult>>
    fun getByText(text: String) : Single<List<SearchResult>>
}