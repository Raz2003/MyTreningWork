package com.example.mytreningwork.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytreningwork.AlertDialog.SaveExecSheet
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.R
import com.example.mytreningwork.model.DB
import com.example.mytreningwork.model.ModelAddExecTraining
import com.example.mytreningwork.model.ModelExecCreate
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelCreateTraining : ViewModel()
{
    var callBackLoad = MutableLiveData<Int>()

    private var mDisposeBag = CompositeDisposable()

    companion object
    {
        var listExec = MutableLiveData<ArrayList<ModelAddExecTraining>>()
        var nameTraining = MutableLiveData<String>()
    }

    fun returnDataExec() : ArrayList<ModelExecCreate>
    {
        val list = ArrayList<ModelExecCreate>()
        list.add(ModelExecCreate("Отжимания", R.drawable.icon_for_test , "pull"))
        list.add(ModelExecCreate("Подтягивания",R.drawable.icon_for_test, "push"))
        list.add(ModelExecCreate("Приседания",R.drawable.icon_for_test, "squats"))
        list.add(ModelExecCreate("Пресс",R.drawable.icon_for_test, "press"))
        return list
    }

    override fun onCleared()
    {
        super.onCleared()
        SaveExecSheet.thisListExec.clear()
        nameTraining.postValue("")
        mDisposeBag.dispose()
    }

    fun saveExecInFirebaseDb() {
        mDisposeBag.add(
            test()
                .subscribeOn(Schedulers.io())
                .subscribe({callBackLoad.postValue(it)},{})
        )
    }

    private fun test() : Single<Int> //загрузка и callbaсk данных
    {
        return Single.create{ rx->
            if(nameTraining.value!=null && listExec.value!!.size>=2){
                val mapName = mutableMapOf<String , Any>()
                mapName["name_training"] = nameTraining.value.toString()
                DB.child("${mAuth.currentUser?.uid.toString()}/CUSTOM_TRAINING/${nameTraining.value.toString()}").updateChildren(mapName)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val mapExec = mutableMapOf<String , Any>()
                            listExec.value!!.forEach{item->
                                mapExec["execName"] = item.node.toString()
                                mapExec["execTask"] = item.task.toInt()
                                mapExec["execCount"] = item.count.toInt()
                                mapExec["execTime"] = item.time.toInt()
                                DB.child("${mAuth.currentUser?.uid.toString()}/CUSTOM_TRAINING/${nameTraining.value.toString()}/${item.node}")
                                    .updateChildren(mapExec)
                            }
                            rx.onSuccess(1)//успешно завершенно
                    }else{rx.onSuccess(2)}//завершено с ошибкой
                }
            }else{rx.onSuccess(3)}//пустое имя или выбрано меньше 2х упражнений
        }
    }
}