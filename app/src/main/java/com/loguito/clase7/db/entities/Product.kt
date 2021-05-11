package com.loguito.clase7.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @ColumnInfo(name = "product_name") val productName: String,
    @ColumnInfo(name = "quantity") val quantity: Int
) {
    @PrimaryKey(autoGenerate = true) var identifier: Int = 0
}