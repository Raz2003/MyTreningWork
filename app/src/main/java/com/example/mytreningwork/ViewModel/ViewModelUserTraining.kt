package com.example.mytreningwork.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.model.DB
import com.example.mytreningwork.model.ModelLoadNameTraining
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelUserTraining : ViewModel()
{
    private var disposeBag = CompositeDisposable()

    private lateinit var mListener : ValueEventListener
    private lateinit var mRef : DatabaseReference

    var liveDataTrainingList = MutableLiveData<List<ModelLoadNameTraining>>()

    init{
        disposeBag.add(
            loadUserTraining()
                .subscribeOn(Schedulers.io())
                .subscribe({liveDataTrainingList.postValue(it)},{})
        )
    }

    private fun loadUserTraining() : Single<List<ModelLoadNameTraining>>
    {
        return Single.create{rx->
            mRef = DB.child("${mAuth.currentUser?.uid.toString()}/CUSTOM_TRAINING")
            mListener = object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    val listData = snapshot.children.map {map->map.getValue(ModelLoadNameTraining::class.java)?:ModelLoadNameTraining()}
                    rx.onSuccess(listData)
                }
                override fun onCancelled(error: DatabaseError){}
            }
            mRef.addListenerForSingleValueEvent(mListener)
        }
    }

    fun loadDataTraining()
    {
        disposeBag.add(
            loadUserTraining()
                .subscribeOn(Schedulers.newThread())
                .subscribe({liveDataTrainingList.postValue(it)},{})
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.dispose()
        mRef.removeEventListener(mListener)
    }
}