package com.egorovfond.dictionary.entities

import com.egorovfond.dictionary.entities.data.SearchResult
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Deferred

interface IDatabase {
    suspend fun getByText(text: String) : List<SearchResult>
}