package com.example.mytreningwork.Fragment

import androidx.fragment.app.Fragment

import com.example.mytreningwork.R
import com.example.mytreningwork.Func.changeFragment
import com.example.mytreningwork.Func.changeFragmentInStack
import com.example.mytreningwork.Func.currentUser
import com.example.mytreningwork.ViewModel.ViewModelDataView
import com.example.mytreningwork.model.*
import kotlinx.android.synthetic.main.fragment_data_if_static.*


class FragmentDataIfStatic : Fragment(R.layout.fragment_data_if_static)
{
    override fun onResume()
    {
        super.onResume()
        if(currentUser.USER_GENDER == "boy") data_gender.setImageResource(R.drawable.male_bg_white)
        your_nick_static.text = currentUser.USER_NICK
        genderImageStatic.changePhotoCropper(currentUser.USER_PHOTO.toString())
        btnClick()
    }

    private fun btnClick()//выбираем раздел отжим. или подтяг.
    {
        btnPushData.setOnClickListener{ViewModelDataView.listNode.add(NODE_ATHLETICS_PUSH_UPS); changeFragmentInStack(FragmentDateView())}
        btnPullData.setOnClickListener{ViewModelDataView.listNode.add(NODE_ATHLETICS_PULL_UPS); changeFragmentInStack(FragmentDateView())}
    }
}