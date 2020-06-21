package com.egorovfond.dictionary.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.entities.data.World
import com.egorovfond.dictionary.presenters.MainPresenter
import com.egorovfond.dictionary.ui.viewmodels.IMainViewHolder
import com.egorovfond.dictionary.ui.viewmodels.IRvMainPresenter
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: MainInteractor) :
BaseViewModel<List<SearchResult>>() {

    private var appState: List<SearchResult>? = null
    lateinit var rvAdapterPresenter: MainPresenter.MainRvAdapterPresenter
    private val TAG = MainViewModel::class.java.toString()

    class MainRvAdapterPresenter: IRvMainPresenter {
        val list = mutableListOf<World>()
        override var onClickListener: ((IMainViewHolder) -> Unit)? = null

        override fun getItemCount(): Int {
            return list.size
        }

        override fun bindViewHolder(holder: IMainViewHolder) {
            list[holder.pos].let {
                holder.setText(it.text)
                holder.setSubmission(it.submission)
            }
            onClickListener = {holder.onClick(list[holder.pos].text, list[holder.pos].imageUrl)}
        }
    }

    fun subscribe(): LiveData<List<SearchResult>> {
        return liveDataForViewToObserve
    }

    override fun findWorld(word: String) {
        cancelJob()
        viewModelCoroutineScope.launch {
            find(word)
        }
    }

    suspend fun find(word: String) {
        val result = interactor.getData(word)
        val words = mutableListOf<World>()
        for (i in result){
            val word = World(
                i.text,
                i.imageUrl,
                stringMeanings(i)
            )
            words.add(word)
        }

        rvAdapterPresenter.list.addAll(words)
    }

    private fun getObserver(): SingleObserver<List<SearchResult>> {
        return object : SingleObserver<List<SearchResult>> {
            override fun onSuccess(t: List<SearchResult>) {

                rvAdapterPresenter.list.clear()
                t?.let {
                    val words = mutableListOf<World>()
                    for (i in it){
                        val word = World(
                            i.text,
                            i.imageUrl,
                            stringMeanings(i)
                        )
                        words.add(word)
                    }

                    rvAdapterPresenter.list.addAll(words)
                }
                }

            override fun onSubscribe(d: Disposable?) {
                d?.let {
                    compositeDisposable.add(it)
                }
            }

            override fun onError(e: Throwable?) {
                Log.d(TAG, "$e")
            }
        }
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