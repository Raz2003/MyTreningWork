package com.example.mytreningwork.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mytreningwork.Fragment.FragmentCreateTraining
import com.example.mytreningwork.Fragment.FragmentViewUserTraining


class ViewPagerAdapterForFragment(fm : FragmentManager, lf : Lifecycle) : FragmentStateAdapter(fm,lf)
{

    private val TAB_TITLES = arrayOf(
            "создание",
            "созданные")

    private val listFragment = arrayListOf<Fragment>(
        FragmentCreateTraining(),
        FragmentViewUserTraining())

    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment{
        return listFragment[position]
    }

    fun getPageTitle(position: Int): CharSequence = TAB_TITLES[position]
}