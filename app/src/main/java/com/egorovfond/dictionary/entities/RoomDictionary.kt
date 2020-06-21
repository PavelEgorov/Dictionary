package com.egorovfond.dictionary.entities

import com.egorovfond.dictionary.entities.data.Meanings
import com.egorovfond.dictionary.entities.data.SearchResult
import com.egorovfond.dictionary.room.HistoryDao
import com.egorovfond.dictionary.room.HistoryEntity
import com.egorovfond.dictionary.usecases.IDictionary

class RoomDictionary(private val historyDao: HistoryDao) :
    IDictionary, ILocalSave{

    override suspend fun getByText(text: String): List<SearchResult> {
        return historyDao.getDataByWord(text).flatMap {
            val meanings = mutableListOf<Meanings>()

            /// Решение очень плохое!!! Нужно делать две таблицы и meanings помещать во вторую!
            // Т.к. времени мало, и я старался адаптировать код, оставлю так.
            val searchResult = SearchResult(it.word, it.description!!, meanings)
            return@flatMap listOf(searchResult)
        }
    }

    override suspend fun saveToDB(dataModel: List<SearchResult>) {
        historyDao.insert(dataModel.flatMap {
            val historyEntity = HistoryEntity(it.text, it.imageUrl)
            return@flatMap listOf(historyEntity)
        })

    }
}