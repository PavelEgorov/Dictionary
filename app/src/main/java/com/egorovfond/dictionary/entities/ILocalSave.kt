package com.egorovfond.dictionary.entities

import com.egorovfond.dictionary.entities.data.SearchResult

interface ILocalSave {
    suspend fun saveToDB(dataModel: List<SearchResult>)
}