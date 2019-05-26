package com.example.presensi.Api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val BASE_URL ="http://presensiugm.herokuapp.com/api/"
    private var retrofit: Retrofit? = null


    val client: Retrofit
        get() {
            if (retrofit == null) {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()


                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!
        }
}