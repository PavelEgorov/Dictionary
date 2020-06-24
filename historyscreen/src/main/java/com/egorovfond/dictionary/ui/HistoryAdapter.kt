package com.egorovfond.dictionary.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.egorovfond.dictionary.R
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.ui.viewmodels.IHistoryAdapter
import com.egorovfond.dictionary.ui.viewmodels.IHistoryViewHolder
import kotlinx.android.synthetic.main.activity_history_recyclerview_item.view.*

class HistoryAdapter (val presenter: IHistoryAdapter): RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<SearchResult> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_history_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.pos = position
        presenter.bindViewHolder(holder)
    }

    override fun getItemCount(): Int {
        return presenter.getItemCount()
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view),
        IHistoryViewHolder {

        override var pos = -1

        override fun bind(data: SearchResult) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.header_history_textview_recycler_item.text = data.text
                itemView.setOnClickListener {
                    Toast.makeText(itemView.context, "on click: ${data.text}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}