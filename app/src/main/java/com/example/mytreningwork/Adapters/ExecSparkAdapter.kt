package com.example.mytreningwork.Adapters

import com.example.mytreningwork.model.ModelExec
import com.robinhood.spark.SparkAdapter

class ExecSparkAdapter(private val list : List<ModelExec>) : SparkAdapter()
{
    override fun getCount(): Int = list.size

    override fun getItem(index: Int): Any = list[index]

    override fun getY(index: Int): Float {
        val chosenDate = list[index]
        return chosenDate.loop.toFloat()
    }
}