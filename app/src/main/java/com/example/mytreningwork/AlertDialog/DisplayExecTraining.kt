package com.example.mytreningwork.AlertDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytreningwork.Adapters.ViewExecTrainigAdapter
import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.ViewModelDisplayTrainingExec
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_dialog_display_training.view.*

class DisplayExecTraining : BottomSheetDialogFragment()
{
    private lateinit var mProvider : ViewModelDisplayTrainingExec

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mProvider = ViewModelProvider(this).get(ViewModelDisplayTrainingExec::class.java)
        val view = View.inflate(this.context, R.layout.bottom_sheet_dialog_display_training,null)

        mProvider.listExec.observe(viewLifecycleOwner , Observer {
            view.RecyclerViewDisplayTrainingExec.apply {
                adapter = ViewExecTrainigAdapter(it)
            }
        })
        return view
    }
}