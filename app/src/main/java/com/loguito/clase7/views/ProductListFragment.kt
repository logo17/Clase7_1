package com.loguito.clase7.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.loguito.clase7.adapter.ProductAdapter
import com.loguito.clase7.databinding.FragmentProductListBinding
import com.loguito.clase7.viewmodel.ProductListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProductListFragment : Fragment() {

    private val adapter = ProductAdapter()
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    val viewModel: ProductListViewModel by viewModels()

    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.productListRecyclerView.adapter = adapter

//        viewModel.getProductList().observe(viewLifecycleOwner) {
//            binding.quantityTextView.text = "${it.size}"
//        }

        disposables.add(
            viewModel.getProductList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.productList = it
                }
        )

        disposables.add(
            adapter.itemClicked
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("PRODUCTLIST", it.productName)
                }
        )
    }
}