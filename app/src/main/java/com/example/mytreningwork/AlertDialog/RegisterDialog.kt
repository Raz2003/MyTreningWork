package com.example.mytreningwork.AlertDialog

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mytreningwork.Activityes.ActivityLoad
import com.example.mytreningwork.Func.changeActivityInFragment
import com.example.mytreningwork.Func.displaySnackBar
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.R
import com.example.mytreningwork.model.DB
import kotlinx.android.synthetic.main.regist_alert_dialog.view.*

class RegisterDialog : DialogFragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val view = View.inflate(this.context,R.layout.regist_alert_dialog,null)
        view.btn_regist_dialog.setOnClickListener {
            val log:String = view.dialog_regist_log.text.toString()
            val pass : String = view.dialog_regist_pass.text.toString()
            if(log.isNotEmpty() && pass.length>=8)
            {
                mAuth.createUserWithEmailAndPassword(log, pass)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            displaySnackBar(R.string.register_successful)
                            val mapUser = mutableMapOf<String , Any>()
                            mapUser["USER_ID"] = mAuth.currentUser?.uid.toString()
                            mapUser["USER_NICK"] = "default"
                            DB.child("${mAuth.currentUser?.uid.toString()}/USER").updateChildren(mapUser)
                                .addOnCompleteListener {load->
                                    if(load.isSuccessful)
                                    {
                                        Handler().postDelayed({changeActivityInFragment(ActivityLoad())},1000)
                                    }
                                }
                        }
                        else{displaySnackBar(R.string.register_error)}
                }
            }else{displaySnackBar(R.string.password_nor_valid)}
        }
        return view
    }
}