package com.egorovfond.dictionary.ui

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.egorovfond.dictionary.R
import com.egorovfond.dictionary.entities.RetrofitDictionary
import com.egorovfond.dictionary.presenters.MainPresenter
import com.egorovfond.dictionary.ui.viewmodels.MainViewModel
import com.egorovfond.dictionary.usecases.DictionaryModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.btn_sheet_main.*


class MainActivity : AppCompatActivity(), MainViewModel {

    lateinit var presenter: MainPresenter
    lateinit var adapter: MainRvAdapter
    lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(AndroidSchedulers.mainThread(),this, DictionaryModel(RetrofitDictionary()))
        presenter.init()

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }
    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun init() {
        rv_main.layoutManager = LinearLayoutManager(applicationContext)
        adapter = MainRvAdapter(presenter.rvAdapterPresenter)
        rv_main.adapter = adapter

//        val fab: FloatingActionButton = findViewById(R.id.fab_main)
//        fab.setOnClickListener { view ->
//            presenter.openFind()
//        }

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.peekHeight = 100
        bottomSheetBehavior.isHideable = false

        btn_find.setOnClickListener {
            edt_find.editText?.let{
                presenter.findWorld(it.text.toString())
            }
        }
        btn_cancel.setOnClickListener {
            presenter.getAll()
        }
    }

    override fun viewEditText() {
        bottomSheetBehavior?.let{
            it.isHideable = false
        }
    }

    override fun hideEditText() {
        edt_find.editText?.let {
        }
        bottomSheetBehavior?.let{
//            it.isHideable = true
        }

    }

    override fun update() {
        adapter?.let{
//            it.notifyDataSetChanged()
        }
    }
}
