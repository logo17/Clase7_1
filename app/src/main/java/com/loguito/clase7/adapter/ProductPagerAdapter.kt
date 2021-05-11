package com.loguito.clase7.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.loguito.clase7.views.CreateProductFragment
import com.loguito.clase7.views.ProductListFragment

class ProductPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            CreateProductFragment()
        } else {
            ProductListFragment()
        }
    }
}