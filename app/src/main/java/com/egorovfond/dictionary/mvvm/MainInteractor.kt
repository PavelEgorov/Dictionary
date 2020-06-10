package com.egorovfond.dictionary.mvvm

import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.usecases.IDictionary
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    val repositoryRemote: IDictionary
) : Interactor<List<SearchResult>> {

    override fun getData(word: String): Single<List<SearchResult>> {
        return repositoryRemote.getByText(word)
    }
}
