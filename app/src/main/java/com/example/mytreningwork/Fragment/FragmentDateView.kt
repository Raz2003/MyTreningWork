package com.example.mytreningwork.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mytreningwork.Activityes.MainActivity
import com.example.mytreningwork.Adapters.SwipeToDel
import com.example.mytreningwork.Adapters.recyclerView

import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.ViewModelDataView
import com.example.mytreningwork.model.*
import kotlinx.android.synthetic.main.fragment_date_view.*


class FragmentDateView : Fragment(R.layout.fragment_date_view)
{
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter : recyclerView
    private lateinit var mProvider : ViewModelDataView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        retainInstance = true
        mProvider = ViewModelProvider(this).get(ViewModelDataView::class.java)
        (activity as MainActivity).supportActionBar?.hide()
    }

    override fun onStart()
    {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView()
    {
        mRecyclerView = date_recycler_view
        mAdapter = recyclerView(childFragmentManager, NODE_ATHLETICS_PUSH_UPS,context!!)
        mRecyclerView.adapter = mAdapter
        mProvider.liveDataList.observe(this, Observer{
            mAdapter.setList(it)
            val mItemTouchHelper = ItemTouchHelper(SwipeToDel(mAdapter))
            mItemTouchHelper.attachToRecyclerView(mRecyclerView)
        })
    }

    override fun onDestroy()
    {
        super.onDestroy()
        ViewModelDataView.listNode.clear()
        mProvider.clearBag()
        (activity as MainActivity).supportActionBar?.show()
    }
}