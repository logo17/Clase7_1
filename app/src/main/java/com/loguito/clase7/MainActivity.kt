package com.loguito.clase7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loguito.clase7.adapter.ProductPagerAdapter
import com.loguito.clase7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ProductPagerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductPagerAdapter(this)

        binding.productsViewPager.adapter = adapter
    }
}