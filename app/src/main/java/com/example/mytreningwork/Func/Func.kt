package com.example.mytreningwork.Func

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.example.mytreningwork.Activityes.MainActivity
import com.example.mytreningwork.ModelCurrentUser
import com.example.mytreningwork.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

lateinit var currentUser : ModelCurrentUser

fun displayToastViewModel(context:Context, msg : String)
{
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun initCurrentUser()
{
    currentUser = ModelCurrentUser()
}

fun Fragment.changeFragmentInStack(fragment : Fragment)
{
    fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.MainContainer,fragment)?.commit()
}

fun Fragment.changeFragment(fragment : Fragment)
{
    fragmentManager?.beginTransaction()?.replace(R.id.MainContainer, fragment)?.commit()
}
fun AppCompatActivity.changeFragmentInActivity(fragment: Fragment)
{
    supportFragmentManager.beginTransaction().replace(R.id.MainContainer,fragment).commit()
}

fun changeActivity(appCompatActivity: AppCompatActivity, activity: Activity)
{
    val intent = Intent(appCompatActivity.baseContext, activity::class.java)
    appCompatActivity.startActivity(intent)
}

fun AppCompatActivity.changeActivityInActivity(activity : Activity)
{
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
}

fun Fragment.changeActivityInFragment(activity: Activity)
{
    val intent = Intent(this.context, activity::class.java)
    startActivity(intent)
}

fun AppCompatActivity.showToastActivity(text : String)
{
    Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
}

lateinit var mAuth : FirebaseAuth

fun Fragment.displaySnackBar( msg : Int)
{
    val sb = Snackbar.make(this.view!!, msg , Snackbar.LENGTH_SHORT)
    sb.setBackgroundTint(resources.getColor(R.color.md_light_blue_A700))
    sb.show()
}