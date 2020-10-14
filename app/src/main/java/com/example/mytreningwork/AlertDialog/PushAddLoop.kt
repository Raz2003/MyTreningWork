package com.example.mytreningwork.AlertDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytreningwork.Fragment.ExecCompanion
import com.example.mytreningwork.Fragment.createMapExec
import com.example.mytreningwork.Fragment.execLoadInDataBase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.push_add_loop.view.*

class PushAddLoop(val ref : String, val layout:Int) : BottomSheetDialogFragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = View.inflate(this.context,layout,null)
        view.bs_btn_save_add_task.setOnClickListener {
            val res = try{view.bs_add_loop.text.toString().toInt() + ExecCompanion.loop}catch (e : NumberFormatException){0}
            if(res!=0) ExecCompanion.count++ ; execLoadInDataBase(createMapExec(res.toString()),ref,2)
        }
        return view
    }
}