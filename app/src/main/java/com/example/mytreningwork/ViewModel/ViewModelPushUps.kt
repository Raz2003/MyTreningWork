package com.example.mytreningwork.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mytreningwork.CustomValueEventListener
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.model.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelPushUps(application : Application) : AndroidViewModel(application)
{
    private var disposeBag = CompositeDisposable()

    var readCurrentData = MutableLiveData<ModelExec>()
    var readAllData = MutableLiveData<List<ModelExec>>()

    private lateinit var refCurrentData : DatabaseReference
    private lateinit var currentDataListener : ValueEventListener


    private lateinit var refAllData : DatabaseReference
    private lateinit var allDataListener : ValueEventListener

    init
    {
        disposeBag.add(
            readDataBase()
                .subscribeOn(Schedulers.io())
                .subscribe{
                    readCurrentData.postValue(it)
                }
        )
        disposeBag.add(
            readAllData()
                .subscribeOn(Schedulers.newThread())
                .subscribe{
                    readAllData.postValue(it)
                }
        )
    }

    private fun readDataBase() : Observable<ModelExec>
    {
        return Observable.create{rx->
            refCurrentData = DB.child(mAuth.currentUser?.uid.toString()).child(NODE_ATHLETICS_PUSH_UPS).child(getCurrentDate())
            currentDataListener = CustomValueEventListener { datasnashot ->
                val map = datasnashot.getValue(ModelExec::class.java) ?: ModelExec()
                rx.onNext(map)
            }
            refCurrentData.addValueEventListener(currentDataListener)
        }
    }

    private fun readAllData() : Observable<List<ModelExec>>
    {
        return Observable.create{rx->
            refAllData = DB.child("${mAuth.currentUser?.uid.toString()}/PUSH_UPS")
            allDataListener = CustomValueEventListener{
                val map = it.children.map {map->map.getValue(ModelExec::class.java)?:ModelExec()}
                rx.onNext(map)
            }
            refAllData.addValueEventListener(allDataListener)
        }
    }

    fun clearBag()
    {
        disposeBag.dispose()
        refCurrentData.removeEventListener(currentDataListener)
        refAllData.removeEventListener(allDataListener)
    }
}