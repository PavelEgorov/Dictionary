package com.egorovfond.dictionary.ui.viewmodels

interface IHistoryAdapter {
    fun getItemCount(): Int
    fun bindViewHolder(holder: IHistoryViewHolder)
}