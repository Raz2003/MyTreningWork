package com.example.mytreningwork.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mytreningwork.CustomValueEventListener
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.date_model
import com.example.mytreningwork.model.DB
import com.example.mytreningwork.model.ModelExec
import com.google.firebase.database.DatabaseReference
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelDataView(application: Application) : AndroidViewModel(application)
{
    companion object{
        var listNode = mutableListOf<String>()
    }

    var liveDataList = MutableLiveData<ArrayList<ModelExec>>()
    private var disposeBag = CompositeDisposable()
    private lateinit var mRef : DatabaseReference
    private lateinit var mListener : CustomValueEventListener


    init
    {
        disposeBag.add(
            loadList()
                .subscribeOn(Schedulers.newThread())
                .subscribe{
                    liveDataList.postValue(it)
                }
        )
    }

    private fun loadList() : Observable<ArrayList<ModelExec>>
    {
        return Observable.create{rx->
            mRef = DB.child("${mAuth.currentUser?.uid.toString()}/${listNode[0]}")
            mListener = CustomValueEventListener {snashot->
                val list = snashot.children.map{map->map.getValue(ModelExec::class.java)?:ModelExec()} as ArrayList<ModelExec>
                rx.onNext(list)
            }
            mRef.addValueEventListener(mListener)
        }
    }

    fun clearBag()
    {
        mRef.removeEventListener(mListener)
        disposeBag.dispose()
    }
}