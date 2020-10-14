package com.example.mytreningwork.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytreningwork.AlertDialog.DisplayExecTraining
import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.ViewModelDisplayTrainingExec
import com.example.mytreningwork.model.ModelLoadNameTraining
import kotlinx.android.synthetic.main.item_user_training.view.*

class UserTrainingAdapter(val list : List<ModelLoadNameTraining>, val fg : FragmentManager,val context: Context) : RecyclerView.Adapter<UserTrainingAdapter.TrainingAdapter>() {

    class TrainingAdapter(view : View) : RecyclerView.ViewHolder(view){
        val name : TextView = view.item_tv_name_training
        val viewTraining : ImageView = view.item_btn_show_training
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_training,parent,false)
        return TrainingAdapter(view)
    }

    override fun onBindViewHolder(holder: TrainingAdapter, position: Int) {
        holder.name.text = list[position].name_training
        holder.viewTraining.setOnClickListener{//открвается dialogFragment и передаём данные
            ViewModelDisplayTrainingExec.nameTraining = list[position].name_training
            Toast.makeText(context,"значени = ${ViewModelDisplayTrainingExec.nameTraining}",Toast.LENGTH_LONG).show()
            DisplayExecTraining().show(fg, "view_user_training")
        }
    }

    override fun getItemCount(): Int = list.size
}