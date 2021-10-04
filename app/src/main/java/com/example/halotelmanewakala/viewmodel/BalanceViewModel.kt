package com.example.halotelmanewakala.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import com.example.halotelmanewakala.db.MobileRepository
import com.romellfudi.ussdlibrary.USSDController.context
import kotlinx.coroutines.flow.collect

class BalanceViewModel (private val repository: MobileRepository): ViewModel(), Observable {
//    val balance= repository.balance

    fun balance() = liveData {
        repository.balance.collect {
            emit(it)
        }
    }

//
//    fun insertBalance(balance: List<Balance>): Job =
//        viewModelScope.launch {
//            repository.insertTestBalance(balance)
//        }
    @Bindable
    val getButtonText =MutableLiveData<String>()

    init {
        getButtonText.value= "FETCH BALANCE"
    }
    fun copyText(text:String){
        val myClipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val myClip: ClipData = ClipData.newPlainText("Label", text)
        myClipboard.setPrimaryClip(myClip)
    }
    fun onGetButton() {

        val myClipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val myClip: ClipData = ClipData.newPlainText("Label","text2323")
        myClipboard.setPrimaryClip(myClip)

//
//        val service= RetroInstance.getRetroInstance()
//        val retrofitData = service?.getDataBalance()

//        if (retrofitData != null) {
//            retrofitData.enqueue(object : Callback<List<Balance>> {
//                override fun onResponse(
//                    call: Call<List<Balance>>,
//                    response: Response<List<Balance>>
//                ) {
//                    val responseBody =response.body()
//                    if (responseBody != null) {
//                        insertBalance(responseBody)
//                    }
//                    Log.d("WAKALAmy", "Balance")
//                }
//
//                override fun onFailure(call: Call<List<Balance>>, t: Throwable) {
//
//                }
//            })
//        }
//        else{
//            Log.d("WAKALA", "omNull")
//        }
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}