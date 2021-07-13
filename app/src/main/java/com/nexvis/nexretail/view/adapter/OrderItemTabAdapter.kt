package com.nexvis.nexretail.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nexvis.nexretail.view.fragment.OrderDetailFragment

class OrderItemTabAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return  when (position) {
            0 -> OrderDetailFragment()
            1 -> OrderDetailFragment()
            2 -> OrderDetailFragment()
            3 -> OrderDetailFragment()

            else -> Fragment()
        }
    }
}