package com.egorovfond.dictionary.ui

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.egorovfond.dictionary.R
import com.egorovfond.dictionary.entities.RetrofitDictionary
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.mvvm.BaseViewModel
import com.egorovfond.dictionary.mvvm.MainInteractor
import com.egorovfond.dictionary.mvvm.MainViewModel
import com.egorovfond.dictionary.presenters.MainPresenter
import com.egorovfond.dictionary.ui.viewmodels.IRvMainPresenter
import com.egorovfond.dictionary.ui.viewmodels.MainView
import com.egorovfond.dictionary.usecases.DictionaryModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.btn_sheet_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import javax.inject.Inject


class MainActivity : BaseActivity<List<SearchResult>, MainInteractor>() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    override val model: MainViewModel by viewModel()
    private val adapter: MainRvAdapter by lazy { MainRvAdapter(model.rvAdapterPresenter) }
    lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //model = viewModelFactory.create(MainViewModel::class.java)
        model.subscribe().observe(this@MainActivity, Observer<List<SearchResult>> { renderData() })

        init()
    }

    override fun renderData() {
        adapter?.let{
            it.notifyDataSetChanged()
        }
    }

    fun init() {
        rv_main.layoutManager = LinearLayoutManager(applicationContext)
        rv_main.adapter = adapter

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.peekHeight = 100
        bottomSheetBehavior.isHideable = false

        btn_find.setOnClickListener {
            edt_find.editText?.let{
                model.findWorld(it.text.toString())
            }
        }
    }
}
