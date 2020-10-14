package com.example.mytreningwork.ViewModel

import android.app.Application
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mytreningwork.Func.currentUser
import com.example.mytreningwork.Func.initCurrentUser
import com.example.mytreningwork.Func.mAuth
import com.example.mytreningwork.ModelCurrentUser
import com.example.mytreningwork.model.DB
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelActivityLoad(application : Application) : AndroidViewModel(application)
{
    private lateinit var mRef : DatabaseReference
    private lateinit var mListener : ValueEventListener
    private val disposeBag = CompositeDisposable()
    var isLoadData = MutableLiveData<Boolean>()

    init
    {
        disposeBag.add(loadDataUser()
            .subscribeOn(Schedulers.newThread())
            .subscribe({
                initCurrentUser()
                currentUser = it
                if(currentUser.USER_ID.isNotEmpty()) isLoadData.postValue(true)},
                {})
            )
    }

    private fun loadDataUser() : Single<ModelCurrentUser>
    {
        return Single.create{
            mRef = DB.child("${mAuth.currentUser?.uid.toString()}/USER")
            mListener = object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    val user = snapshot.getValue(ModelCurrentUser::class.java)?:ModelCurrentUser()
                    it.onSuccess(user)
                }
                override fun onCancelled(error: DatabaseError) {}
            }
            mRef.addListenerForSingleValueEvent(mListener)
        }
    }

    fun clearBag()
    {
        disposeBag.dispose()
        mRef.removeEventListener(mListener)
    }

    override fun onCleared()
    {
        super.onCleared()
        clearBag()
    }
}