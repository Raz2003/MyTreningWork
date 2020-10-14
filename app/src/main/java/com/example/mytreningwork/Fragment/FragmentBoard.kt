package com.example.mytreningwork.Fragment

import android.app.ActionBar
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytreningwork.Activityes.MainActivity
import com.example.mytreningwork.Adapters.BaseRecyclerViewAdapter
import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.ViewModelFragmentBoard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_board.*

class FragmentBoard : Fragment(R.layout.fragment_board)
{
    private lateinit var mProvider : ViewModelFragmentBoard

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mProvider = ViewModelProvider(this).get(ViewModelFragmentBoard::class.java)
        (activity as MainActivity).supportActionBar?.hide()
    }

    override fun onResume()
    {
        super.onResume()
        mProvider.dataBoard.observe(this , Observer {
            val recyclerView = RecyclerViewBoard
            recyclerView.apply {
                adapter = BaseRecyclerViewAdapter(it)
            }
        })
    }

    override fun onDestroy()
    {
        super.onDestroy()
        mProvider.clearBag()
    }
}