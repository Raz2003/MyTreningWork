package com.example.mytreningwork.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mytreningwork.CustomValueEventListener
import com.example.mytreningwork.model.DB
import com.example.mytreningwork.model.ModelBoardUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelFragmentBoard(application : Application) : AndroidViewModel(application)
{
    private var bag = CompositeDisposable()
    var dataBoard = MutableLiveData<List<ModelBoardUser>>()
    private lateinit var ref : DatabaseReference
    private lateinit var listener : ValueEventListener

    init
    {
        bag.add(
            loadUserBoard()
                .map {it.sortedBy {map->map.USER_SCORE}}
                .subscribeOn(Schedulers.io())
                .subscribe{
                    dataBoard.postValue(it)
                }
        )
    }

    private fun loadUserBoard() : Observable<List<ModelBoardUser>>
    {
        return Observable.create{rx->
            ref = DB.child("BOARD")
            listener = CustomValueEventListener{
                val list = it.children.map {map -> map.getValue(ModelBoardUser::class.java)?:ModelBoardUser()}
                rx.onNext(list)
            }
            ref.addValueEventListener(listener)
        }
    }

    fun clearBag()
    {
        bag.dispose()
        ref.removeEventListener(listener)
    }
}