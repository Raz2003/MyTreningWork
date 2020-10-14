package com.example.mytreningwork.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytreningwork.AlertDialog.ExecDialog
import com.example.mytreningwork.Func.currentUser
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.R
import com.example.mytreningwork.model.DB
import com.example.mytreningwork.model.ModelExec
import com.example.mytreningwork.model.changePhotoCropper
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.data_item.view.*

class recyclerView(val fragmentManager: FragmentManager, val node : String, val context : Context) : RecyclerView.Adapter<recyclerView.recyclerViewHolder>()
{
    var mListExec = ArrayList<ModelExec>()

    class recyclerViewHolder(view : android.view.View) : RecyclerView.ViewHolder(view)
    {
        val textViewDate : TextView = view.tv_item_data
        val userPhoto : CircleImageView = view.image_user_photo
        val userRes : ImageView = view.image_res
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data_item,parent,false)
        return recyclerViewHolder(view)
    }

    override fun getItemCount(): Int = mListExec.size

    override fun onBindViewHolder(holder: recyclerViewHolder, position: Int)
    {
        if(mListExec[position].loop.toInt() >= 500) holder.userRes.setBackgroundResource(R.drawable.ic_strong_blue)
        holder.userPhoto.changePhotoCropper(currentUser.USER_PHOTO.toString())
        holder.userPhoto.animation = AnimationUtils.loadAnimation(context, R.anim.anim_rv_for_icon_user)
        holder.textViewDate.text = mListExec[position].data.replace("-",",")
        holder.itemView.setOnClickListener {
            val dialog = ExecDialog()
            ExecDialog.mapExec["data"] = mListExec[position].data.replace("-",".")
            ExecDialog.mapExec["loop"] = mListExec[position].loop
            ExecDialog.mapExec["task"] = mListExec[position].task
            ExecDialog.mapExec["touch"] = mListExec[position].touch
            if(ExecDialog.mapExec.isNotEmpty())
            {
                dialog.show(fragmentManager, "exec_dialog")
            }
        }
    }

    fun setList(list: ArrayList<ModelExec>)
    {
        mListExec = list
        notifyDataSetChanged()
    }

    fun delItem(position : Int)
    {
        DB.child("${mAuth.currentUser?.uid.toString()}/$node/${mListExec[position].data}").removeValue()
        mListExec.removeAt(position)
        notifyItemRemoved(position)
    }
}