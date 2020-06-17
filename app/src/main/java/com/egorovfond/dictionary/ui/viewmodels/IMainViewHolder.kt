package com.egorovfond.dictionary.ui.viewmodels

interface IMainViewHolder {
    var pos: Int

    fun setText(text: String)
    fun setSubmission(text: String)

    fun onClick(text: String, url: String)
}