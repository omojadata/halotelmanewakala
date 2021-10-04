package com.example.halotelmanewakala.viewmodel



import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*

import com.example.halotelmanewakala.db.MobileRepository
import com.example.halotelmanewakala.db.WakalaMkuu
import com.example.halotelmanewakala.network.RetroInstance
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WakalaMkuuViewModel(private val repository: MobileRepository,application: Application):AndroidViewModel(application), Observable {
//    val getModePreference= DataStorePreference(application)

    val wakalaMkuu=repository.wakalaMkuu


//    val balance= getModePreference.balanceMode.asLiveData().observe(this,{
//        getModePreference.saveBalanceMode(it)
//    })


    @Bindable
    val getButtonText =MutableLiveData<String>()

    @Bindable
    val getButtonTextBalance =MutableLiveData<String>()

    init {
        getButtonText.value= "FETCH WAKALA MKUU"
        getButtonTextBalance.value= "FETCH BALANCE"
    }

    fun insert(wakalaMkuu: List<WakalaMkuu>): Job =
        viewModelScope.launch {
            repository.insertWakalaMkuu(wakalaMkuu)

        }



    fun onGetButton() {
        val service= RetroInstance.getRetroInstance()
        val retrofitData = service?.getDataWakalaMkuu()

        if (retrofitData != null) {
            retrofitData.enqueue(object : Callback<List<WakalaMkuu>> {
                override fun onResponse(
                    call: Call<List<WakalaMkuu>>,
                    response: Response<List<WakalaMkuu>>
                ) {
                    val responseBody =response.body()
                    if (responseBody != null) {
                        insert(responseBody)
                    }
                    Log.d("WAKALAmy", "WakalaMkuu")
                }
                override fun onFailure(call: Call<List<WakalaMkuu>>, t: Throwable) {

                }
            })
        }
        else{
            Log.d("WAKALA", "omNull")
        }
    }



    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}