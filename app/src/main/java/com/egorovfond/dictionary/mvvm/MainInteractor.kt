package com.egorovfond.dictionary.mvvm

import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.usecases.IDictionary
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    val repositoryRemote: IDictionary
) : Interactor<List<SearchResult>> {

    override suspend fun getData(word: String) = withContext(Dispatchers.IO) {
        repositoryRemote.getByText(word)
    }
}
