package com.loguito.clase7.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.loguito.clase7.db.entities.Product
import com.loguito.clase7.repositories.ProductRepository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//M V VM

interface CreateProductViewModelInputs {
    val name: Observer<String>
    val quantity: Observer<Int>
    val saveClicked: Observer<Unit>
}

interface CreateProductViewModelOutputs {
    val isButtonEnabled: Observable<Boolean>
    val nameError: Observable<Boolean>
    val productCreated: Observable<Boolean>
}

interface CreateProductViewModelType {
    val inputs: CreateProductViewModelInputs
    val outputs: CreateProductViewModelOutputs
}

class CreateProductViewModel(application: Application) : AndroidViewModel(application), CreateProductViewModelInputs, CreateProductViewModelOutputs, CreateProductViewModelType {
    private var repository: ProductRepository = ProductRepository(application.applicationContext)

    // TYPE

    override val inputs: CreateProductViewModelInputs = this
    override val outputs: CreateProductViewModelOutputs = this

    // INPUTS

    override val name = BehaviorSubject.create<String>()
    override val quantity = BehaviorSubject.create<Int>()
    override val saveClicked = PublishSubject.create<Unit>()

    // OUTPUTS

    override val isButtonEnabled: Observable<Boolean>
    override val nameError: Observable<Boolean>
    override val productCreated: Observable<Boolean>

    init {
        isButtonEnabled = Observable.combineLatest(name, quantity, {n, q -> n.isNotEmpty() && q > 0})

        nameError = name
            .map { it.isEmpty() }

        productCreated = saveClicked
            .withLatestFrom(name, quantity, {e, n, q -> Product(productName = n, quantity = q)})
            .doOnNext { createProduct(it) }
            .map { true }
    }

    fun createProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertProduct(product)
        }
    }
}