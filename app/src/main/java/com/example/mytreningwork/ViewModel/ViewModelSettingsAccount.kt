package com.example.mytreningwork.ViewModel

import android.app.Application
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mytreningwork.Func.displaySnackBar
import com.example.mytreningwork.Func.displayToastViewModel
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.model.DB

class ViewModelSettingsAccount(application: Application) : AndroidViewModel(application)
{
    val userGender = MutableLiveData<String>()


    fun loadGenderInBase()
    {
        DB.child("${mAuth.currentUser?.uid.toString()}/USER/USER_GENDER").setValue(userGender.value)
            .addOnCompleteListener{
            if(it.isSuccessful)
            {
                displayToastViewModel(getApplication(), "Данные обновились!")
            }
        }
    }
}