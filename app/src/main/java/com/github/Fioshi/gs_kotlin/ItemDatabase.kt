package com.github.Fioshi.gs_kotlin


import androidx.room.Database
import androidx.room.RoomDatabase
import nellefb.com.github.listadecompras.ItemDao

@Database(entities = [ItemModel::class], version = 2)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}