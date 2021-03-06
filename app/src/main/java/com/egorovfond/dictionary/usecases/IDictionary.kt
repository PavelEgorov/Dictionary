package com.egorovfond.dictionary.usecases

import com.egorovfond.dictionary.entities.data.SearchResult

interface IDictionary {
    suspend fun getByText(text: String) : List<SearchResult>
}