package com.example.mytreningwork.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytreningwork.*
import com.example.mytreningwork.Activityes.RegisterActivity
import com.example.mytreningwork.AlertDialog.SettingsSheetDialog
import com.example.mytreningwork.Func.changeActivityInFragment
import com.example.mytreningwork.Func.currentUser
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.ViewModel.ViewModelSettingsAccount
import com.example.mytreningwork.model.DB
import com.example.mytreningwork.model.changePhoto
import com.example.mytreningwork.model.changePhotoCropper
import com.example.mytreningwork.model.displayToast

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.fragment_change_name.*

class FragmentSettingsAccount : Fragment(R.layout.fragment_change_name)
{
    lateinit var mProvider : ViewModelSettingsAccount

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        mProvider = ViewModelProvider(this).get(ViewModelSettingsAccount::class.java)
    }

    private fun initFields()
    {
        your_nick_settings.text = currentUser.USER_NICK
        genderImageStatic.changePhotoCropper(currentUser.USER_PHOTO.toString())
        if(currentUser.USER_GENDER == "boy") settings_gender.setImageResource(R.drawable.male_bg_white)
    }

    override fun onStart()
    {
        super.onStart()
        val toolbar = view?.findViewById<Toolbar>(R.id.setting_toolbar)
        (activity as AppCompatActivity).title = ""
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        initFields()
    }

    override fun onResume()
    {
        super.onResume()
        mProvider.userGender.observe(this, Observer {//userGender
            mProvider.loadGenderInBase()
        })

        genderImageStatic.setOnClickListener{ changePhoto() }//изменение фото профиля пользоваетя

        btn_change_name.setOnClickListener {
            val dialog = SettingsSheetDialog()
            dialog.show(childFragmentManager,"test")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)//функция изменяющая фото профиля
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && data != null)
        {
            val uri = CropImage.getActivityResult(data).uri
            val ref = FirebaseStorage.getInstance().reference.child("account_photo/${mAuth.currentUser?.uid.toString()}")
            ref.putFile(uri).addOnCompleteListener{ task ->
                if(task.isSuccessful)
                {
                    ref.downloadUrl.addOnCompleteListener{task1->
                        if(task1.isSuccessful)
                        {
                            val photoUrl = task1.result.toString()
                            DB.child("${mAuth.currentUser?.uid.toString()}/USER/USER_PHOTO").setValue(photoUrl).addOnCompleteListener{
                                if(it.isSuccessful)
                                {
                                    genderImageStatic.changePhotoCropper(photoUrl)
                                    currentUser.USER_PHOTO = photoUrl
                                }
                            }
                        }
                    }
                }
                displayToast("Фото успешно обновилось!")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_acount,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.exit_account -> {FirebaseAuth.getInstance().signOut()
            changeActivityInFragment(RegisterActivity())}
        }
        return false
    }
}