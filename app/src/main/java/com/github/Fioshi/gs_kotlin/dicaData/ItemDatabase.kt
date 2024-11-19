package com.github.Fioshi.gs_kotlin.dicaData


import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.Fioshi.gs_kotlin.dicaModel.ItemModel

@Database(entities = [ItemModel::class], version = 2)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}