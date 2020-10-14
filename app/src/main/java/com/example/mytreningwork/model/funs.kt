package com.example.mytreningwork.model

import androidx.fragment.app.Fragment
import com.example.mytreningwork.Activityes.MainActivity
import com.example.mytreningwork.R
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import de.hdodenhof.circleimageview.CircleImageView



fun CircleImageView.changePhotoCropper(ref : String)
{
    Picasso.get().load(ref).placeholder(R.drawable.ic_person_sign).into(this)
}

fun Fragment.changePhoto()
{
    CropImage
        .activity()
        .setAspectRatio(1,1)//1,1 - пропорциональность
        .setRequestedSize(600,600)//обрезание if(image > 600), то он её обрезает
        .setCropShape(CropImageView.CropShape.OVAL)
        .start((activity as MainActivity),this)//в нашем фрагменте
}