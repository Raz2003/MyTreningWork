package com.example.mytreningwork.AlertDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytreningwork.Func.displaySnackBar
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.R
import com.example.mytreningwork.model.DB
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_settings.view.*

class SettingsSheetDialog : BottomSheetDialogFragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = View.inflate(this.context,R.layout.bottom_sheet_settings, null)
        view.btn_sheet_dialog_save_nick.setOnClickListener {
            val nick = view.ed_text_sheet_dialog_nick.text.toString()

            if(nick.isNotEmpty() && nick.length<=20)
            {
                DB.child("${mAuth.currentUser?.uid.toString()}/USER/USER_NICK").setValue(nick)
            }
        }
        view.btn_boy.setOnClickListener {
            val gender = "boy"
            DB.child("${mAuth.currentUser?.uid.toString()}/USER/USER_GENDER").setValue(gender)
        }

        view.btn_girl.setOnClickListener {
            val gender = "girl"
            DB.child("${mAuth.currentUser?.uid.toString()}/USER/USER_GENDER").setValue(gender)
        }

        return view
    }
}