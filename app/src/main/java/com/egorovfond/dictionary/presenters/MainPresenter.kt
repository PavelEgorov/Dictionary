package com.egorovfond.dictionary.presenters

import android.util.Log
import com.egorovfond.dictionary.ui.viewmodels.IMainViewHolder
import com.egorovfond.dictionary.ui.viewmodels.IRvMainPresenter
import com.egorovfond.dictionary.ui.viewmodels.MainView
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.usecases.IDictionary
import com.egorovfond.dictionary.entities.data.World
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class MainPresenter(
    val mainThread: Scheduler,
    val viewModel: MainView,
    val dictionary: IDictionary
) {

    lateinit var rvAdapterPresenter: MainRvAdapterPresenter
    val disposable = CompositeDisposable()
    private val TAG = MainPresenter::class.java.toString()

    class MainRvAdapterPresenter: IRvMainPresenter{
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
        }
    }

    fun init() {
        rvAdapterPresenter = MainRvAdapterPresenter()
        viewModel.init()
    }

    fun openFind(){
        viewModel.viewEditText()
    }

    fun findWorld(world: String){
        viewModel.hideEditText()
        dictionary.getByText(world)
            .observeOn(mainThread)
            .subscribeWith(getObserver())
    }

    fun getAll(){
        // пока не реализую, т.к. не увидел запроса на получение всех слов в api
//        dictionary.getAll()
//            .observeOn(mainThread)
//            .subscribeWith(getObserver())
    }

    fun onStart(){
        this.getAll()
    }

    fun onStop(){
        disposable.clear()
    }

    private fun getObserver(): SingleObserver<List<SearchResult>> {
        return object : SingleObserver<List<SearchResult>> {
            override fun onSuccess(t: List<SearchResult>) {

                rvAdapterPresenter.list.clear()
                t?.let {
                    val words = mutableListOf<World>()
                    for (i in it){
                        val word = World(i.text, stringMeanings(i))
                        words.add(word)
                    }

                    rvAdapterPresenter.list.addAll(words)
                }
                viewModel.update()
                viewModel.hideEditText()
            }

            override fun onSubscribe(d: Disposable?) {
                d?.let {
                    disposable.add(it)
                }
            }

            override fun onError(e: Throwable?) {
                Log.d(TAG, "$e")
                viewModel.hideEditText()
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