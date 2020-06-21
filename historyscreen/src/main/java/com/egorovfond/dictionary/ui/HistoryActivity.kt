package com.egorovfond.dictionary.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import com.egorovfond.dictionary.R
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.mvvm.HistoryInteractor
import com.egorovfond.dictionary.mvvm.HistoryViewModel
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity<List<SearchResult>, HistoryInteractor>() {

    override val model: HistoryViewModel by viewModel()
    private val adapter: HistoryAdapter by lazy { HistoryAdapter(model.rvAdapterPresenter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        model.findWorld("")
    }

    private fun iniViewModel() {
        if (history_activity_recyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        model.subscribe().observe(this@HistoryActivity, Observer<List<SearchResult>> { renderData() })
    }

    override fun renderData() {
        adapter?.let{
            it.notifyDataSetChanged()
        }
    }

    private fun initViews() {
        history_activity_recyclerview.adapter = adapter
    }
}
