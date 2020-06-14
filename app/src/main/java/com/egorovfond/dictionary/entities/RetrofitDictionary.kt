package com.egorovfond.dictionary.entities

import javax.inject.Inject

class RetrofitDictionary @Inject constructor(): IDatabase {
    override fun getByText(text: String)= ApiHolder.api.search(text)
}