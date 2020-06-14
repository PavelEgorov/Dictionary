package com.egorovfond.dictionary.entities

import com.egorovfond.dictionary.entities.data.SearchResult
import io.reactivex.rxjava3.core.Single

interface IDatabase {
    fun getByText(text: String) : Single<List<SearchResult>>
}