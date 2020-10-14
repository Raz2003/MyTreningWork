package com.example.mytreningwork.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.model.DB
import com.example.mytreningwork.model.ModelAddExecTraining
import com.example.mytreningwork.model.ModelLoadTrainingExec
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.NumberFormatException

class ViewModelDisplayTrainingExec : ViewModel()
{
    private val disposeBag = CompositeDisposable()
    var listExec = MutableLiveData<List<ModelAddExecTraining>>()

    companion object{
        var nameTraining = ""
    }

    init {
        disposeBag.add(
            loadData()
                .subscribeOn(Schedulers.newThread())
                .subscribe({//listExec.postValue(it)
                },{})
        )

    }

    //НЕИЗВЕСТНАЯ ОШИБКА В КОНВЕРТАЦИИ ДАННЫХ
    private fun loadData(): Single<List<ModelAddExecTraining>>
    {
        return Single.create{rx->
            val ref = FirebaseDatabase.getInstance().reference
                .child(mAuth.currentUser?.uid.toString())
                .child("CUSTOM_TRAINING")
                .child("pull")
            val listener = object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    try{
                        val list = snapshot.children.map{it.getValue(ModelAddExecTraining::class.java)?:ModelAddExecTraining()}
                        rx.onSuccess(list)
                    }catch (e : NumberFormatException){}
                }
                override fun onCancelled(error: DatabaseError) {}
            }
            ref.addListenerForSingleValueEvent(listener)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.dispose()
        nameTraining = ""
    }
}