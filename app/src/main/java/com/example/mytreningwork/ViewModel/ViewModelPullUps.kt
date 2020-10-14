package com.example.mytreningwork.ViewModel

import android.app.Application
import android.view.animation.AnimationUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mytreningwork.CustomValueEventListener
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.R
import com.example.mytreningwork.model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_push_ups.*

class ViewModelPullUps(application : Application) : AndroidViewModel(application)
{
    private var mDisposeBag = CompositeDisposable()
    var readData = MutableLiveData<ModelExec>()
    var readAllData = MutableLiveData<List<ModelExec>>()

    private lateinit var refCurrentData : DatabaseReference
    private lateinit var listenerCurrentData : ValueEventListener

    private lateinit var refAllData : DatabaseReference
    private lateinit var listenerAllData : ValueEventListener

    init
    {
        mDisposeBag.add(
            readDataBase()
                .subscribeOn(Schedulers.io())
                .subscribe{
                    readData.postValue(it)
                }
        )
        mDisposeBag.add(
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
            refCurrentData = DB.child(mAuth.currentUser?.uid.toString()).child(NODE_ATHLETICS_PULL_UPS).child(getCurrentDate())
            listenerCurrentData = object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot)
                    {
                        val map = snapshot.getValue(ModelExec::class.java)?:ModelExec()
                        rx.onNext(map)
                    }
                    override fun onCancelled(error: DatabaseError) {}
                }
            refCurrentData.addValueEventListener(listenerCurrentData)
        }
    }

    private fun readAllData() : Observable<List<ModelExec>>
    {
        return Observable.create{rx->
            refAllData = DB.child("${mAuth.currentUser?.uid.toString()}/PULL_UPS")
            listenerAllData = CustomValueEventListener{snapshot ->
                val list = snapshot.children.map {map->map.getValue(ModelExec::class.java)?:ModelExec()}
                rx.onNext(list)
            }
            refAllData.addValueEventListener(listenerAllData)
        }
    }

    fun clearBag()
    {
        mDisposeBag.dispose()
        refCurrentData.removeEventListener(listenerCurrentData)
        refAllData.removeEventListener(listenerAllData)
    }
}