package com.loguito.clase7.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.loguito.clase7.db.entities.Product
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {
//    @Query("SELECT * FROM product")
//    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM product")
    fun getAllProducts(): Observable<List<Product>>

    @Insert
    suspend fun insertProduct(product: Product)
}