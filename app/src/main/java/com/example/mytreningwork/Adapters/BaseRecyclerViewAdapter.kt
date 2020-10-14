package com.example.mytreningwork.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytreningwork.R
import com.example.mytreningwork.model.ModelBoardUser
import kotlinx.android.synthetic.main.layout_board.view.*

class BaseRecyclerViewAdapter(private val list : List<ModelBoardUser>) : RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder>()
{
    class BaseViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val nick : TextView = view.tvNick
        val score : TextView = view.tvScore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_board,parent,false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int)
    {
        holder.score.text = list[position].USER_SCORE
        holder.nick.text = list[position].USER_NICK
    }

    override fun getItemCount(): Int = list.size
}