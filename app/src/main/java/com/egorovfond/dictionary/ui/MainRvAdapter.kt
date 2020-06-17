package com.egorovfond.dictionary.ui

import android.content.Intent.getIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.egorovfond.dictionary.R
import com.egorovfond.dictionary.ui.viewmodels.IMainViewHolder
import com.egorovfond.dictionary.ui.viewmodels.IRvMainPresenter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_rv_main.view.*

class MainRvAdapter(val presenter : IRvMainPresenter): RecyclerView.Adapter<MainRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_rv_main, parent, false))

    override fun getItemCount(): Int = presenter.getItemCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos  =   position
        holder.containerView.setOnClickListener{
            presenter.onClickListener?.invoke(holder)
        }
        presenter.bindViewHolder(holder)
    }

    class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer, IMainViewHolder {
        override var pos = -1

        override fun setText(text: String) = with(containerView){
            txt_title.text = text
        }

        override fun setSubmission(text: String) = with(containerView){
            txt_submission.text = text
        }

        override fun onClick(text: String, url: String) = with(containerView){
            this.context.startActivity(
                ImageActivity.getIntent(
                    this.context,
                    text,
                    url
                )
            )
        }
    }
}