package com.example.mytreningwork.AlertDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.ViewModelCreateTraining
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.change_name_training_view.view.*

class SheetDialogChangeNameTraining : BottomSheetDialogFragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = View.inflate(this.context, R.layout.change_name_training_view,null)
        view.btn_save_name_command.setOnClickListener {
            val name = view.ed_new_name_command.text.toString()
            if(name.isNotEmpty())
            {
                ViewModelCreateTraining.nameTraining.postValue(name)
            }
        }
        return view
    }
}