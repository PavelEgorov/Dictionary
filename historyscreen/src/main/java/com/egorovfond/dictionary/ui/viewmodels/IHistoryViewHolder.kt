package com.egorovfond.dictionary.ui.viewmodels

import com.egorovfond.dictionary.entities.data.SearchResult

interface IHistoryViewHolder {
    var pos: Int
    fun bind(data: SearchResult)
}