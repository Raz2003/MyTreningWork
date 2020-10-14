package com.example.mytreningwork.Activityes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.mytreningwork.*
import com.example.mytreningwork.Fragment.*
import com.example.mytreningwork.Func.*
import com.example.mytreningwork.databinding.ActivityMainBinding
import com.example.mytreningwork.model.DB
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity()
{
    private lateinit var mBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        DB = FirebaseDatabase.getInstance().reference
    }

    override fun onStart()
    {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        initApp()
    }

    private fun initApp()
    {
        initUser(this){
            changeFragmentInActivity(FragmentHome())
            clickBottomItem()
        }
    }

    private fun clickBottomItem()
    {
        main_nav_bottom.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.item_data -> changeFragmentInActivity(FragmentDataIfStatic())
                R.id.item_sport -> changeFragmentInActivity(FragmentSportReplace())
                R.id.item_settings -> changeFragmentInActivity(FragmentSettingsAccount())
                R.id.item_home -> changeFragmentInActivity(FragmentHome())
            }
            true
        }
    }
}