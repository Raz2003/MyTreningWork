package com.example.mytreningwork.Activityes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytreningwork.AlertDialog.RegisterDialog
import com.example.mytreningwork.Func.changeActivityInActivity
import com.example.mytreningwork.Func.showToastActivity
import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.CustomTextWatcher
import com.example.mytreningwork.ViewModel.ViewModelRegisterActivity
import com.example.mytreningwork.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity()
{
    private lateinit var mBinding : ActivityRegisterBinding
    private lateinit var mToolbar : Toolbar

    private  var pass : String = ""
    private  var login : String = ""

    private lateinit var mProvider : ViewModelRegisterActivity

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mProvider = ViewModelProvider(this).get(ViewModelRegisterActivity::class.java)
    }

    override fun onStart()
    {
        super.onStart()
        initToolbar()
        register_btn_sign.isEnabled = false
    }

    override fun onResume()
    {
        super.onResume()

        register_btn_sign.setOnClickListener {
            mProvider.authUser(login, pass)
            {
                changeActivityInActivity(ActivityLoad())
            }
        }

        register_btn_reg.setOnClickListener {openDialog()}

        register_input_email.addTextChangedListener(CustomTextWatcher{
            login = it.toString()
            listenerText()
        })

        register_input_password.addTextChangedListener(CustomTextWatcher{
            pass = it.toString()
            listenerText()
        })
    }

    override fun onDestroy()
    {
        super.onDestroy()
        finish()
    }

    private fun openDialog()
    {
        val dialog = RegisterDialog()
        dialog.show(supportFragmentManager, "test")
    }

    private fun listenerText()
    {
        if(pass.length >= 6 && login.length >= 4)
        {
            image_view_lock.visibility = View.INVISIBLE
            register_btn_sign.isEnabled = true
        }else{
            image_view_lock.visibility = View.VISIBLE
            register_btn_sign.isEnabled = false
        }
    }

    private fun initToolbar()
    {
        mToolbar = mBinding.RegisterToolbar
        setSupportActionBar(mToolbar)
        title = "Регистрация/Авторизация"
    }
}