package com.example.mytreningwork.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytreningwork.AlertDialog.SaveExecSheet
import com.example.mytreningwork.R
import com.example.mytreningwork.model.ModelExecCreate
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.item_training_exec.view.*

class BaseExecAdapter(val listData : ArrayList<ModelExecCreate>,val fragmentManager: FragmentManager) : RecyclerView.Adapter<BaseExecAdapter.BaseExecViewHolder>()
{
    class BaseExecViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val nameExec : TextView = view.item_name_exec
        val imageExec : ImageView = view.item_image_exec
        val btnAdd : FloatingActionButton = view.item_btn_save_exec
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseExecViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_training_exec,parent,false)
        return BaseExecViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseExecViewHolder, position: Int) {
        holder.nameExec.text = listData[position].name_exec
        holder.imageExec.setImageResource(listData[position].image_exec)
        holder.btnAdd.setOnClickListener {
            SaveExecSheet(listData[position].name_node,listData[position].name_exec).show(fragmentManager,"test")//sheet для добавдение данных
        }
    }

    override fun getItemCount(): Int = listData.size
}