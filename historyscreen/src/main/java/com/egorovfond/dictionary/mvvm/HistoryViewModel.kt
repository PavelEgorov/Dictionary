package com.egorovfond.dictionary.mvvm

import androidx.lifecycle.LiveData
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.ui.viewmodels.IHistoryAdapter
import com.egorovfond.dictionary.ui.viewmodels.IHistoryViewHolder

class HistoryViewModel(private val interactor: HistoryInteractor) :
    BaseViewModel<List<SearchResult>>() {

    val rvAdapterPresenter =
        HistoryRvAdapterPresenter()

    class HistoryRvAdapterPresenter: IHistoryAdapter {
        val list = mutableListOf<SearchResult>()

        override fun getItemCount(): Int {
            return list.size
        }

        override fun bindViewHolder(holder: IHistoryViewHolder) {
            list[holder.pos].let {
                holder.bind(it)
            }
        }
    }
    fun subscribe(): LiveData<List<SearchResult>> {
        return liveDataForViewToObserve
    }

    private suspend fun startInteractor(word: String) {
        val result = interactor.getData(word)
         rvAdapterPresenter.list.addAll(result)
    }

    override fun findWorld(word: String) {
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word) }
    }

    private fun stringMeanings(searchResult: SearchResult): String {
        val result = StringBuffer()

        searchResult.meanings?.let {
            for (i in it)  i.translation?.let{
                it.text?.let {
                    result.append(" ${it}, ")
                }
            }
        }

        return result.toString()
    }
}