package com.example.mytreningwork.AlertDialog

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.ViewModelCreateTraining
import com.example.mytreningwork.model.ModelAddExecTraining
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.push_add_loop.view.*
import java.lang.NumberFormatException

class SaveExecSheet(private val nameNode:String, private val nameExec : String) : BottomSheetDialogFragment()
{
    companion object{
        var thisListExec = ArrayList<ModelAddExecTraining>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)//прозрачный фон bg
        val view = View.inflate(this.context , R.layout.push_add_loop,null)

        view.bs_btn_save_add_task.setOnClickListener {

            val resTask = try{view.bs_add_loop.text.toString().toInt()}catch (e : NumberFormatException){0}//результат
            val resCount = try{view.bs_add_count.text.toString().toInt()}catch (e : NumberFormatException){0}//подходы
            val resTime = try{view.bs_add_time.text.toString().toInt()}catch (e : NumberFormatException){0}//время

            if(resTask!=0){
                thisListExec.add(ModelAddExecTraining(task = resTask, count = resCount,
                    time = resTime,node = nameNode, name = nameExec))
                ViewModelCreateTraining.listExec.postValue(thisListExec)
            }

            view.bs_add_loop.setText("")
            view.bs_add_count.setText("")
            view.bs_add_time.setText("")

            Log.e(TAG, "size = ${ViewModelCreateTraining.listExec.value?.size}")
        }
        return view
    }
}