package com.example.halotelmanewakala.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.halotelmanewakala.db.MobileRepository

class FloatOutViewModelFactory(private val repository: MobileRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?>create(modelClass: Class<T>):T{
        if(modelClass.isAssignableFrom(FloatOutViewModel::class.java)){
            return  FloatOutViewModel(repository) as T
        }
        throw  IllegalArgumentException("Unknown View Model Class")
    }
}