package com.example.mytreningwork.AlertDialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mytreningwork.R
import kotlinx.android.synthetic.main.exec_alert_dialog.view.*

class ExecDialog : DialogFragment()
{
    companion object{
        var mapExec = mutableMapOf<String , String>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val view = View.inflate(this.context, R.layout.exec_alert_dialog,null)

        view.ad_exec_data.text = "дата ${mapExec["data"]}"
        view.ad_exec_loop.text = "выполнено ${mapExec["loop"]}"
        view.ad_exec_task.text = "задача ${mapExec["task"]}"
        view.ad_exec_touch.text = "подходы ${mapExec["touch"]}"
        return view
    }

}