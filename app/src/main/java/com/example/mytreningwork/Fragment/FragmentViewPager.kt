package com.example.mytreningwork.Fragment

import androidx.fragment.app.Fragment
import com.example.mytreningwork.Adapters.ViewPagerAdapterForFragment
import com.example.mytreningwork.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_view_pager.*


class FragmentViewPager : Fragment(R.layout.fragment_view_pager)
{
    private lateinit var mAdapter : ViewPagerAdapterForFragment

    override fun onStart(){
        super.onStart()
        mAdapter = ViewPagerAdapterForFragment(childFragmentManager,lifecycle)
        fg_view_pager_exec.adapter = mAdapter
        TabLayoutMediator(fg_view_pager_exec_tab_layout, fg_view_pager_exec){tab,pos->
            tab.text = mAdapter.getPageTitle(pos)
        }.attach()
    }
}