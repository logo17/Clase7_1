package com.loguito.clase7.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.loguito.clase7.db.entities.Product
import com.loguito.clase7.repositories.ProductRepository
import io.reactivex.Observable


class ProductListViewModel(application: Application) : AndroidViewModel(application) {
    val repository = ProductRepository(application.applicationContext)

//    fun getProductList() : LiveData<List<Product>> {
//        return repository.getAllProducts()
//    }

    fun getProductList(): Observable<List<Product>> {
        return repository.getAllProducts()
    }
}