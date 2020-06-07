package com.egorovfond.dictionary.entities

class RetrofitDictionary: IDatabase {
    override fun getAll() = ApiHolder.api.meanings()
    override fun getByText(text: String)= ApiHolder.api.search(text)
}