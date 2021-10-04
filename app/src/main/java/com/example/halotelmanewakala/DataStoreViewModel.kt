package com.example.halotelmanewakala

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataStoreViewModel(application: Application) :AndroidViewModel(application){

    private val datastoree= DataStorePreference(application)


    val getDataStore=datastoree.autoMode


    fun  saveToDataStore(isAutoMode:Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            datastoree.saveAutoMode(isAutoMode)
        }
    }

}