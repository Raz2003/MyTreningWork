package com.example.mytreningwork.Adapters

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDel(var adapter: recyclerView) : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT)
{
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean
    {
        TODO("not implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
    {
        val position = viewHolder.adapterPosition
        adapter.delItem(position)
    }
}