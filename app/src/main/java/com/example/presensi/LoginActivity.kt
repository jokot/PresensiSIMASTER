package com.example.presensi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.presensi.Api.LoginRequest
import com.example.presensi.Api.LoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(),View.OnClickListener {

    val app = MainApp()
    val service = app.service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUpClick()
        isLogin()
    }

    private fun isLogin(){
        if(getToken() != MainActivity.DEFAULT){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    private fun setUpClick(){
        btn_masuk.setOnClickListener(this)
        tv_register.setOnClickListener(this)
        tv_lupa.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_masuk -> login()
            R.id.tv_register -> register()
            R.id.tv_lupa -> register()
        }
    }

    private fun register(){
        val url = "http://presensiugm.herokuapp.com/login"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun login(){
        progress_bar.visibility = View.VISIBLE
        val email = et_email.text.toString()
        val password = et_password.text.toString()

        val request = LoginRequest(email,password)

        val call = service.login(request)

        call.enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                progress_bar.visibility = View.GONE
                toast(t.message.toString())
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                response.let{loginResponse ->
                    if(loginResponse.isSuccessful) loginResponse.body()?.let {
                        saveToken(it.token)
                        logD(it.token)
                        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                        finish()
                    }else{
                        toast("Gagal Login, pastikan email dan password benar")
                    }
                    progress_bar.visibility = View.GONE
                }
            }
        })
    }
}
