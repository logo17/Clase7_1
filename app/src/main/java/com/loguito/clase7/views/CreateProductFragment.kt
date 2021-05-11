package com.loguito.clase7.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.loguito.clase7.databinding.FragmentCreateProductBinding
import com.loguito.clase7.viewmodel.CreateProductViewModel
import com.loguito.clase7.viewmodel.CreateProductViewModelType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CreateProductFragment : Fragment() {
    private var _binding: FragmentCreateProductBinding? = null
    private val binding get() = _binding!!

    val disposables = CompositeDisposable()

    lateinit var viewModel: CreateProductViewModelType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(CreateProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.button.setOnClickListener {
//            viewModel.createProduct(productNameEditText.text.toString(), productQuantityEditText.text.toString().toInt())
//        }

        disposables.add(
            RxTextView.textChanges(binding.productNameEditText)
                .skipInitialValue()
                .map { it.toString() }
                .subscribe {
                    viewModel.inputs.name.onNext(it)
                }
        )

        disposables.add(
            RxTextView.textChanges(binding.productQuantityEditText)
                .map { if (it.isEmpty()) 0 else it.toString().toInt() }
                .subscribe {
                    viewModel.inputs.quantity.onNext(it)
                }
        )

        disposables.add(
            RxView.clicks(binding.button)
                .subscribe {
                    viewModel.inputs.saveClicked.onNext(Unit)
                }
        )

        disposables.add(
            viewModel.outputs.isButtonEnabled
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.button.isEnabled = it
                }
        )

        disposables.add(
            viewModel.outputs.nameError
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.textInputLayout.error = if (it) "Campo requerido" else null
                }
        )

        disposables.add(
            viewModel.outputs.productCreated
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.productNameEditText.setText("")
                    binding.productQuantityEditText.setText("")
                }
        )
    }
}