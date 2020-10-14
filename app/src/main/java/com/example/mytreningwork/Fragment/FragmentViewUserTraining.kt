package com.example.mytreningwork.Fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytreningwork.Adapters.UserTrainingAdapter

import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.ViewModelUserTraining
import kotlinx.android.synthetic.main.fragment_view_user_training.*


class FragmentViewUserTraining : Fragment(R.layout.fragment_view_user_training)
{
    private lateinit var mProvider : ViewModelUserTraining

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mProvider = ViewModelProvider(this).get(ViewModelUserTraining::class.java)
    }

    override fun onStart()
    {
        super.onStart()
        mProvider.liveDataTrainingList.observe(this , Observer {
            RecyclerViewUserTraining.apply {
                adapter = UserTrainingAdapter(it,childFragmentManager,context)
            }
        })

        rf_update_data_user_training.apply {
            setColorSchemeColors(Color.parseColor("#0091EA"))//md_blue_700a
            setOnRefreshListener {
                mProvider.loadDataTraining()
                isRefreshing = false
            }
        }
    }
}
