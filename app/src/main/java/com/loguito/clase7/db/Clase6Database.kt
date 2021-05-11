package com.loguito.clase7.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.loguito.clase7.db.dao.ProductDAO
import com.loguito.clase7.db.entities.Product

@Database(entities = arrayOf(Product::class), version = 1, exportSchema = false)
abstract class Clase6Database : RoomDatabase() {
    abstract fun productDao(): ProductDAO

    companion object {
        @Volatile
        private var INSTANCE: Clase6Database? = null

        fun getDatabase(context: Context): Clase6Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Clase6Database::class.java,
                    "product_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}