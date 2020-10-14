package com.example.mytreningwork.Fragment

import androidx.fragment.app.Fragment

import com.example.mytreningwork.R
import com.example.mytreningwork.Func.changeFragment
import com.example.mytreningwork.Func.changeFragmentInStack
import com.example.mytreningwork.Func.currentUser
import com.example.mytreningwork.model.*
import kotlinx.android.synthetic.main.fragment_sport_replace.*


class FragmentSportReplace : Fragment(R.layout.fragment_sport_replace)
{
    override fun onStart()
    {
        super.onStart()
        if(currentUser.USER_GENDER == "boy")gender.setImageResource(R.drawable.male_bg_white)
        genderImageSport.changePhotoCropper(currentUser.USER_PHOTO.toString())
        your_nick_sport.text = currentUser.USER_NICK
        fragmentClickBtn()

    }

    private fun fragmentClickBtn()
    {
        btnPushAdd.setOnClickListener{changeFragmentInStack(FragmentPushUps())}
        btnPullAdd.setOnClickListener { changeFragmentInStack(FragmentPullUps())}
        btnCreateTraining.setOnClickListener{changeFragmentInStack(FragmentViewPager())}
    }
}