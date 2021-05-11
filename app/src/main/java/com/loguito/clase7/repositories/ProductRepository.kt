package com.loguito.clase7.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.loguito.clase7.db.Clase6Database
import com.loguito.clase7.db.entities.Product
import io.reactivex.Observable

class ProductRepository(context: Context) {
    var db: Clase6Database = Clase6Database.getDatabase(context)

//    fun insertProduct(product: Product) {
//        db.productDao().insertProduct(product)
//    }

    suspend fun insertProduct(product: Product) {
        db.productDao().insertProduct(product)
    }

//    fun getAllProducts(): LiveData<List<Product>> = db.productDao().getAllProducts()

    fun getAllProducts(): Observable<List<Product>> = db.productDao().getAllProducts()
}