package com.example.mytreningwork.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytreningwork.R
import com.example.mytreningwork.model.ModelAddExecTraining
import kotlinx.android.synthetic.main.item_training_view_exec.view.*

class ViewExecTrainigAdapter(val list : List<ModelAddExecTraining>) : RecyclerView.Adapter<ViewExecTrainigAdapter.ViewExecTrainigAdapterViewHolder>()
{
    class ViewExecTrainigAdapterViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val name : TextView = view.view_name
        val count : TextView = view.view_count
        val task : TextView = view.view_task
        val time : TextView = view.view_time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewExecTrainigAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_training_view_exec,parent,false)
        return ViewExecTrainigAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewExecTrainigAdapterViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.count.text = list[position].count.toString()
        holder.task.text = list[position].task.toString()
        holder.time.text = list[position].time.toString()
    }

    override fun getItemCount(): Int = list.size


}