package com.example.mytreningwork.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytreningwork.Adapters.BaseExecAdapter
import com.example.mytreningwork.Adapters.CreateTrainingExecAdapter
import com.example.mytreningwork.AlertDialog.SheetDialogChangeNameTraining
import com.example.mytreningwork.Func.displaySnackBar
import com.example.mytreningwork.R
import com.example.mytreningwork.ViewModel.ViewModelCreateTraining
import kotlinx.android.synthetic.main.fragment_create_training.*

class FragmentCreateTraining : Fragment(R.layout.fragment_create_training)
{
    private lateinit var mProvider : ViewModelCreateTraining

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        mProvider = ViewModelProvider(this).get(ViewModelCreateTraining::class.java)
    }

    override fun onStart()
    {
        super.onStart()
        RecyclerViewCreateExec.apply {
            adapter = BaseExecAdapter(mProvider.returnDataExec(),childFragmentManager)
        }
    }

    override fun onResume()
    {
        super.onResume()
        ViewModelCreateTraining.listExec.observe(this , Observer {//recyclerView с вашими упражнениями
            fg_create_tr_tv_list.text = "Список упражнений:"
            RecyclerViewShowTaskMyTraining.apply {
                adapter = CreateTrainingExecAdapter(it)
            }
        })

        mProvider.callBackLoad.observe(this, Observer {//отслеживание progressBar
            when(it)
            {
                1->{stopAnimDisplaySnackBar(R.string.complete_load_data)}//успешная загрузка данных
                2->{stopAnimDisplaySnackBar(R.string.error_load_data)}//ошибка при загрузке данных
                3->{stopAnimDisplaySnackBar(R.string.empty_name_or_empty_load_data)}//ошибка при вводе имени или меньше 2х элементов листа
            }
        })

        ViewModelCreateTraining.nameTraining.observe(this, Observer {
            fg_create_tr_name_new_command.text = "Название тренировки: $it"
        })

        fg_create_tr_btn_save.setOnClickListener {//при нажатии на конпку , она скрывается и начинается анимация progress bar
            fg_create_tr_btn_save.visibility = View.GONE
            fg_create_tr_progress_bar_load.visibility = View.VISIBLE
            mProvider.saveExecInFirebaseDb()
        }

        fg_create_tr_btn_new_name_tr.setOnClickListener {//Окрытие dialog для измении имени тренировки
            SheetDialogChangeNameTraining().show(childFragmentManager, "")
        }
    }

    private fun stopAnimDisplaySnackBar(msg : Int)
    {
        fg_create_tr_btn_save.visibility = View.VISIBLE
        fg_create_tr_progress_bar_load.visibility = View.GONE
        displaySnackBar(msg)
    }
}