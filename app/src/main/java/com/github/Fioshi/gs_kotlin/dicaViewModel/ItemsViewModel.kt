package com.github.Fioshi.gs_kotlin.dicaViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.github.Fioshi.gs_kotlin.dicaData.ItemDao
import com.github.Fioshi.gs_kotlin.dicaData.ItemDatabase
import com.github.Fioshi.gs_kotlin.dicaModel.ItemModel


class ItemsViewModel(application: Application) : AndroidViewModel(application) {
    private val itemDao: ItemDao
    val itemsLiveData: LiveData<List<ItemModel>>
    init {

        val database = Room.databaseBuilder(
            getApplication(),
            ItemDatabase::class.java,
            "items_database"
        ).build()
        itemDao = database.itemDao()

        itemsLiveData = itemDao.getAll()

        val items = itemsLiveData.value
        if (items.isNullOrEmpty()) {
            itemsLiveData.apply {
                addItem("Lampada","de led")
            }
        }
    }

    fun addItem(item: String, desc: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = ItemModel(name = item, desc = desc)
            itemDao.insert(newItem)
        }

    }

    fun removeItem(item: ItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }
}