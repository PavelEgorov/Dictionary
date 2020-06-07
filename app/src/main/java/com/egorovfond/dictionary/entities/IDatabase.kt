package com.egorovfond.dictionary.entities

import com.egorovfond.dictionary.entities.data.SearchResult
import io.reactivex.rxjava3.core.Single

interface IDatabase {
    fun getAll() : Single<List<SearchResult>>
    fun getByText(text: String) : Single<List<SearchResult>>
}