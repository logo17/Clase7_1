package com.loguito.clase7.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loguito.clase7.databinding.ProductCellBinding
import com.loguito.clase7.db.entities.Product
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val clicksAcceptor = PublishSubject.create<Product>()

    val itemClicked: Observable<Product> = clicksAcceptor.hide()

    var productList: List<Product> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ProductViewHolder(private val binding: ProductCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productNameTextView.text = product.productName
            binding.productQuantityTextView.text = product.quantity.toString()

            binding.root.setOnClickListener {
                clicksAcceptor.onNext(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size
}