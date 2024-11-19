package com.github.Fioshi.gs_kotlin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val desc: String
)