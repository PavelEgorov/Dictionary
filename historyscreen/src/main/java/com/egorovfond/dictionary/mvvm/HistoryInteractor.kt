package com.egorovfond.dictionary.mvvm

import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.usecases.IDictionary

class HistoryInteractor(
    private val repositoryRemote: IDictionary
) : Interactor<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
        return repositoryRemote.getByText(word)

    }
}
