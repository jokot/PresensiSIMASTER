package com.example.presensi

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import com.example.presensi.Api.ApiClient
import com.example.presensi.Api.ApiServices
import com.example.presensi.Api.LogoutResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import retrofit2.*


@SuppressLint("Registered")
class MainApp: Application() {

//    private val retrofit: Retrofit = Retrofit.Builder()
//        .baseUrl(ApiClient.BASE_URL)
//        .addConverterFactory(ScalarsConverterFactory.create())
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

    var gson = GsonBuilder()
        .setLenient()
        .create()

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ApiClient.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val service: ApiServices = retrofit.create(ApiServices::class.java)

    fun logoutDatabase(activity:Activity){
        startActivity(Intent(activity,LoginActivity::class.java))
        activity.finishAffinity()
    }

    fun logoutWeb(tokenLogin:String,
        onFailur:(String)-> Unit,
        onSuccesfull:(LogoutResponse)-> Unit,
        onError:(String)-> Unit){
        val call = service.logout(tokenLogin)
        call.enqueue(object : Callback<LogoutResponse> {
            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                onFailur(t.message.toString())
            }

            override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                response.let { responseLogout->
                    if(responseLogout.isSuccessful) responseLogout.body()?.let {
                        onSuccesfull(it)
                    }else responseLogout.errorBody()!!.string().let{
                        onError(it)
                    }
                }
            }

        })
    }

}