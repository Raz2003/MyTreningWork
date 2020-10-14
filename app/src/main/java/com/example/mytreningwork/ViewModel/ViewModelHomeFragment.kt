package com.example.mytreningwork.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mytreningwork.CustomValueEventListener
import com.example.mytreningwork.Fragment.Score
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.model.DB
import com.example.mytreningwork.model.ModelBoard
import com.example.mytreningwork.model.ModelExec
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelHomeFragment(application: Application) : AndroidViewModel(application)
{
    var loadBoardPushUps = MutableLiveData<List<ModelExec>>()
    var loadBoard = MutableLiveData<ModelBoard>()
    var loadBoardPullUps = MutableLiveData<List<ModelExec>>()

    private var bag = CompositeDisposable()

    private lateinit var refPushUps : DatabaseReference
    private lateinit var listenerPushUps : ValueEventListener

    private lateinit var refBoard : DatabaseReference
    private lateinit var listenerBoard : ValueEventListener

    private lateinit var refBoardPullUps : DatabaseReference
    private lateinit var listenerBoardPullUps : ValueEventListener

    init
    {
        bag.add(//отжимания
            loadBoardPushUps()
                .subscribeOn(Schedulers.io())
                .subscribe({loadBoardPushUps.postValue(it)},{})
        )

        bag.add(
            loadBoard()
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    loadBoard.postValue(it)
                    Score.userScore = it.USER_SCORE.toInt()
                },{})
        )

        bag.add(//подтягивания
            loadBoardPullUps()
                .subscribeOn(Schedulers.newThread())
                .subscribe({loadBoardPullUps.postValue(it)},{})
        )

    }

    private fun loadBoardPullUps() : Single<List<ModelExec>>//ПОДТЯГИВАНИЯ
    {
        return Single.create{ rx->
            refBoardPullUps = DB.child("${mAuth.currentUser?.uid.toString()}/PULL_UPS")
            listenerBoardPullUps = CustomValueEventListener{
                val map = it.children.map {map->map.getValue(ModelExec::class.java)?:ModelExec()}
                rx.onSuccess(map)
            }
            refBoardPullUps.addListenerForSingleValueEvent(listenerBoardPullUps)
        }
    }

    private fun loadBoardPushUps() : Single<List<ModelExec>>//ОТЖИМАНИЯ
    {
        return Single.create{rx->
            refPushUps = DB.child("${mAuth.currentUser?.uid.toString()}/PUSH_UPS")
            listenerPushUps = object  : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.children.map{map-> map.getValue(ModelExec::class.java)?:ModelExec()}
                    rx.onSuccess(data)
                }
                override fun onCancelled(error: DatabaseError){}
            }
            refPushUps.addListenerForSingleValueEvent(listenerPushUps)
        }
    }

    private fun loadBoard() : Single<ModelBoard>
    {
        return Single.create{rx->
            refBoard = DB.child("BOARD/${mAuth.currentUser?.uid.toString()}")
            listenerBoard = CustomValueEventListener{dataSnapshot ->  
                val resMap = dataSnapshot.getValue(ModelBoard::class.java)?:ModelBoard()
                rx.onSuccess(resMap)
            }
            refBoard.addValueEventListener(listenerBoard)
        }
    }

    private fun clearBag()
    {
        bag.dispose()
        refBoard.removeEventListener(listenerBoard)
        refPushUps.removeEventListener(listenerPushUps)
        refBoardPullUps.removeEventListener(listenerBoardPullUps)
        Score.userScore = 0
    }

    override fun onCleared()
    {
        super.onCleared()
        clearBag()
    }
}