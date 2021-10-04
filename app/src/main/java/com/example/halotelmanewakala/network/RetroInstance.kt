package com.example.halotelmanewakala.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {
        val baseUrl = "https://abcwakala.omojadata.com/"
        fun getRetroInstance(): RetroService? {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetroService::class.java)
        }
    }
}
