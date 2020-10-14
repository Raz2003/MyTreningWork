package com.example.mytreningwork.Activityes

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.Func.showToastActivity
import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.ViewModelActivityLoad
import com.example.mytreningwork.model.DB
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_load.*

class ActivityLoad : AppCompatActivity()
{
    private lateinit var mProvider : ViewModelActivityLoad

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)

        DB = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()

        mProvider = ViewModelProvider(this).get(ViewModelActivityLoad::class.java)

        if(mAuth.currentUser != null)
        {
            mProvider.isLoadData.observe(this, Observer {
                startActivity(Intent(this , MainActivity::class.java));finish()
            })
        }else{startActivity(Intent(this, RegisterActivity::class.java));finish()}
    }

    override fun onStart()
    {
        super.onStart()
        if(!checkConnection()){
            actv_load_text_view_load.apply{
                text = "Нет подключения к интенету.."
                setTextColor(Color.RED)
            }
        }
    }

    private fun checkConnection() : Boolean
    {
        val connection = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connectionInfo = connection.activeNetworkInfo
        if(connectionInfo != null && connectionInfo.isConnected) return true
        return false
    }
}