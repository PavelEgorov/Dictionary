package com.egorovfond.dictionary.entities

import com.egorovfond.dictionary.entities.data.SearchResult
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Deferred<List<SearchResult>>
}