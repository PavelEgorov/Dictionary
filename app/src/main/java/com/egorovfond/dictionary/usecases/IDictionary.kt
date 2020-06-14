package com.egorovfond.dictionary.usecases

import com.egorovfond.dictionary.entities.data.SearchResult
import io.reactivex.rxjava3.core.Single

interface IDictionary {
    suspend fun getByText(text: String) : List<SearchResult>
}