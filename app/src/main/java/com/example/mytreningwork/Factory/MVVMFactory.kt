package com.example.mytreningwork.Factory

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MVVMFactory(val view : View) :  ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MVVMFactory(view) as T
    }
}