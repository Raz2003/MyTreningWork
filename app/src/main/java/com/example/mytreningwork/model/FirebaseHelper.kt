package com.example.mytreningwork.model

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.date_model
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

lateinit var Exec : exercises

const val  NODE_ATHLETICS_PUSH_UPS = "PUSH_UPS"
const val  NODE_ATHLETICS_PULL_UPS = "PULL_UPS"
const val CHILD_DATA = "data"
const val CHILD_lOOP = "loop"
const val CHILD_TOUCH = "touch"
const val CHILD_TASK = "task"
lateinit var DB : DatabaseReference

fun getCurrentDate() : String {return SimpleDateFormat("dd-MM-yy").format(Date())}

 fun writeDataDb(map : MutableMap<String,Any>,node_main : String)
{
    DB.child(mAuth.currentUser?.uid.toString()).child(node_main).child(getCurrentDate()).updateChildren(map)
}

fun Fragment.displayToast(msg : String)
{
    Toast.makeText(this.context, msg,Toast.LENGTH_SHORT).show()
}