package com.example.mytreningwork.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytreningwork.Adapters.ExecSparkAdapter
import com.example.mytreningwork.AlertDialog.PushAddLoop
import com.example.mytreningwork.AlertDialog.PushAddTask
import com.example.mytreningwork.Func.displaySnackBar

import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.ViewModelPushUps
import com.example.mytreningwork.model.ModelExec
import kotlinx.android.synthetic.main.fragment_push_ups.*
import kotlinx.android.synthetic.main.fragment_push_ups.cardView2
import kotlinx.android.synthetic.main.fragment_push_ups.push_progress_bar_exec
import kotlinx.android.synthetic.main.fragment_push_ups.pull_spark_view

class FragmentPushUps : Fragment(R.layout.fragment_push_ups) {

    private lateinit var mProvider : ViewModelPushUps

    private var pushLoop : String = "0"
    private var pushCount : String = "0"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mProvider = ViewModelProvider(this).get(ViewModelPushUps::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onStart()
    {
        super.onStart()
        initAnim()
        mProvider.readCurrentData.observe(this , Observer {
            ExecCompanion.loop = it.loop.toInt()
            ExecCompanion.count = it.touch.toInt()
            pushLoop = it.loop
            pushCount = it.touch
            push_board_loop.text = "${it.loop}/${it.task}"
            push_progress_bar_exec.max = it.task.toInt()
            push_progress_bar_exec.progress = it.loop.toInt()
            push_board_task.text = "Задача: ${it.task}"
            push_board_count.text = "Подход: ${it.touch}"
            push_board_date.text = it.data.replace("-", ".")
        })

        mProvider.readAllData.observe(this , Observer {
            pull_spark_view.apply {
                lineWidth = 3f
                isScrubEnabled = true
                lineColor = resources.getColor(R.color.md_white_1000)
                adapter = ExecSparkAdapter(it)
                scrubLineColor = resources.getColor(R.color.md_light_blue_A400)
            }
            pull_spark_view.setScrubListener { itemData->//прослушивание нажатий
                if(itemData is ModelExec)
                {
                    push_spark_board_date.text = "Дата: ${itemData.data.replace('-','.')}"
                    push_spark_board_loop.text = "Кол-во: ${itemData.loop}"
                    push_spark_board_count.text = "Подходы: ${itemData.touch}"
                    push_spark_board_task.text = "Цель: ${itemData.task}"
                }
            }
        })
    }

    override fun onResume()
    {
        super.onResume()
        push_btn_add_loop.setOnClickListener {
            if(pushLoop.toInt() <= pushLoop.toInt())
            {
                val dialog = PushAddLoop("PUSH_UPS",R.layout.add_exec_layout)
                dialog.show(parentFragmentManager,"dialog")
            } else displaySnackBar(R.string.task_complete)
        }
        push_btn_add_task.setOnClickListener {
            val dialog = PushAddTask("PUSH_UPS",R.layout.add_exec_layout)
            dialog.show(parentFragmentManager,"dialog")
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        mProvider.clearBag()
        replaceExec()
    }

    private fun initAnim()
    {
        val animDate = AnimationUtils.loadAnimation(this.context, R.anim.anim_card_home)
        cardView2.startAnimation(animDate)
    }
}