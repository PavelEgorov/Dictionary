package com.egorovfond.dictionary.ui.viewmodels

interface IRvMainPresenter {
    var onClickListener: ((IMainViewHolder) -> Unit)?

    fun getItemCount(): Int
    fun bindViewHolder(holder: IMainViewHolder)
}