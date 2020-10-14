package com.example.mytreningwork.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.ViewModelCreateTraining
import com.example.mytreningwork.model.DB
import com.example.mytreningwork.model.ModelAddExecTraining
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.item_save_exec.view.*

class CreateTrainingExecAdapter(val list : ArrayList<ModelAddExecTraining>) : RecyclerView.Adapter<CreateTrainingExecAdapter.TrainingExecViewHolder>()
{
    class TrainingExecViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val name : TextView = view.item_save_exec_name
        val task : TextView = view.item_save_exec_task
        val count : TextView = view.item_save_exec_count
        val time : TextView = view.item_save_exec_time
        val del : FloatingActionButton = view.item_del
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingExecViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_save_exec,parent,false)
        return TrainingExecViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TrainingExecViewHolder, position: Int)
    {
        holder.name.text = list[position].name
        holder.task.text = "Задача: ${list[position].task}"
        holder.count.text = "Подходы: ${list[position].count}"
        holder.time.text = "Время: ${list[position].time}"
        holder.del.setOnClickListener {
            list.remove(list[position])//при нажатии на кнопку удалить
            ViewModelCreateTraining.listExec.postValue(list) //вкладываем лист с удалённыем значением

            val testMap = mutableMapOf<String,Any>()
            testMap["count"] = list[position].count
            testMap["name"] = list[position].name
            testMap["task"] = list[position].task
            testMap["time"] = list[position].time

            DB.child("${mAuth.currentUser?.uid.toString()}/CUSTOM_TRAINING/${list[position].node}").updateChildren(testMap)
        }
    }

    override fun getItemCount(): Int = list.size
}