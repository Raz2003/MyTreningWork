package com.example.mytreningwork.ViewModel

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import com.example.mytreningwork.AlertDialog.RegisterDialog
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.R
import com.google.firebase.auth.FirebaseAuth

class ViewModelRegisterActivity(application : Application) : AndroidViewModel(application)
{
    inline fun authUser(login : String , pass : String,crossinline function : () -> Unit)
    {
        mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(login, pass)
            .addOnCompleteListener{
                if(it.isSuccessful)
                {
                    function()
                }else{Toast.makeText(getApplication(), "Неверный логин или пароль!",Toast.LENGTH_SHORT).show()}
            }
    }
}