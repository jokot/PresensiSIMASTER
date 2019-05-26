package com.example.presensi

import android.annotation.SuppressLint
import android.app.Application
import com.example.presensi.Api.ApiClient
import com.example.presensi.Api.ApiServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

@SuppressLint("Registered")
class MainApp: Application() {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ApiClient.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: ApiServices = retrofit.create(ApiServices::class.java)

}