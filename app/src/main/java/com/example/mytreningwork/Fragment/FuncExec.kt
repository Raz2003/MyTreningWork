package com.example.mytreningwork.Fragment

import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.mytreningwork.Func.currentUser
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.ViewModelCreateTraining
import com.example.mytreningwork.model.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_push_ups.*

class ExecCompanion
{
    companion object
    {
        var count : Int = 0
        var loop : Int = 0
    }
}

class Score {
    companion object{
        var userScore : Int = 0
    }
}


fun replaceExec()
{
    ExecCompanion.count = 0
    ExecCompanion.loop = 0
}

fun replaceImageViewWeak(pushUps : String, push_ups_image_weak : ImageView)
{
    val res : Int = pushUps.toInt()
    if(res >= 500)
    {
        push_ups_image_weak.setImageResource(R.drawable.ic_strong)
    }else{
        push_ups_image_weak.setImageResource(R.drawable.ic_weak)
    }
}

fun execLoadInDataBase(map : Map<String , Any>, node : String , execScore : Int)
{
    DB.child("${mAuth.currentUser?.uid.toString()}/$node/${getCurrentDate()}").updateChildren(map)
        .addOnCompleteListener { task->
            if(task.isSuccessful)
            {
                val res = map["loop"].toString().toInt() * execScore
                DB.child("BOARD/${mAuth.currentUser?.uid.toString()}/USER_NICK").setValue(currentUser.USER_NICK)
                DB.child("BOARD/${mAuth.currentUser?.uid.toString()}/USER_SCORE").setValue((Score.userScore + res).toString())//записываем очки за упражнение
            }
    }
}

fun createMapExec(loop : String) : Map<String , Any>
{
    val map = mutableMapOf<String , Any>()
    map["data"] = getCurrentDate()
    map["loop"] = loop
    map["touch"] = ExecCompanion.count.toString()//подсчёт подходов
    return map
}

fun createTask(task : String,node: String)
{
    DB.child("${mAuth.currentUser?.uid.toString()}/$node/${getCurrentDate()}/task").setValue(task)
}