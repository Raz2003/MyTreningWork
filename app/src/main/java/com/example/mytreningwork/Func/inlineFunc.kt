package com.example.mytreningwork.Func

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.mytreningwork.Activityes.RegisterActivity

inline fun initUser(appCompatActivity: AppCompatActivity,crossinline function:()-> Unit)
{
    if(mAuth.currentUser != null)
    {
        function()
    }else{
        changeActivity(appCompatActivity, RegisterActivity())
    }
}