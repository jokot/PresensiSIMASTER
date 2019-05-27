package com.example.presensi

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.presensi.Api.AbsenRequest
import com.example.presensi.Api.AbsenResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), FragmentChange, View.OnClickListener {


    private var qrcode: String? = null
    private val permission = arrayOf(android.Manifest.permission.CAMERA)
    private val app = MainApp()
    private val services = app.service

    override fun onFragmentChange(fragment: Fragment) {
        changeFragment(fragment)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_beranda -> {
                changeFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_kehadiran -> {
                changeFragment(KehadiranFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notif -> {
                changeFragment(NotifiFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_akun -> {
                changeFragment(UserFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        qrcode = intent.getStringExtra("token")

        if (qrcode != null) {
            absen()
        }

        floating_btn.bringToFront()
        floating_btn.setOnClickListener(this)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_container, HomeFragment())
            .commit()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.floating_btn -> scanQrCode()
        }
    }

    private fun absen() {
//        progress_bar.visibility = View.VISIBLE
        val tokenLogin = "Bearer ${getToken()}"
        val qrCode = qrcode!!.split(".")
        if (qrCode.size == 2 ){
            val meetingId = qrCode[0].toInt()
            val token = qrCode[1]
            logD(tokenLogin)
            logD(meetingId.toString())
            logD(token)

            val request = AbsenRequest(meetingId, token)
            val call = services.scanQrCode(tokenLogin, request)
            call.enqueue(object :Callback<AbsenResponse>{
                override fun onFailure(call: Call<AbsenResponse>, t: Throwable) {
//                progress_bar.visibility = View.GONE
                    toast(t.message.toString())
                }

                override fun onResponse(call: Call<AbsenResponse>, response: Response<AbsenResponse>) {
                    response.let {absenResponse->
                        if (absenResponse.isSuccessful) absenResponse.body().let {
                            val course ="${it!!.subject.kode} ${it.subject.name}"
                            showDialog(it.code,course,it.message)
                            logD("${it.status}${it.code}")
                        }else response.errorBody()!!.string().let{
                            logD(it)
                            if(it == "{\"message\":\"Unauthenticated.\"}"){
                                toast("Sesion Anda sudah habis, silakan login")
                                logoutDatabase()
                            }else{
                                val code  = it.substring(26,30).toInt()
                                logD(code.toString())
                                val kode = it.substring(57,64)
                                logD(kode)
                                showDialog(code, kode,"gagal")
                            }

                        }
//                    progress_bar.visibility = View.GONE
                    }
                }
            })
        }else{
            showDialog(4100, "MIIII","gagal")
        }
    }

    private fun logoutDatabase(){
        logout()
        startActivity(Intent(this,LoginActivity::class.java))
        finishAffinity()
//        app.logoutDatabase(this)
    }

    private fun showDialog(code: Int,course:String,message:String) {
        val layoutInflater = LayoutInflater.from(this)
        val parentDialog = layoutInflater.inflate(R.layout.custom_dialog, null)

        val tvStatus = parentDialog.findViewById<TextView>(R.id.tv_status)
        val ivStatus = parentDialog.findViewById<ImageView>(R.id.iv_status)
        val tvKeterangan = parentDialog.findViewById<TextView>(R.id.tv_keterangan)
        val tvMatkul = parentDialog.findViewById<TextView>(R.id.tv_matkul)
        val button = parentDialog.findViewById<Button>(R.id.btn_dialog)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        var alert: AlertDialog = builder.create()

        if (code == 1100) {
            tvMatkul.text = course
        } else {
            tvStatus.text = "Gagal"
            tvStatus.setTextColor(ContextCompat.getColor(this, R.color.red))
            ivStatus.visibility = View.GONE
            tvMatkul.text = "Silakan coba lagi"
            when(code){
                2100->{
                    tvKeterangan.text = "Anda tidak joint kelas ini"
                }
                3100->{
                    tvKeterangan.text = "Kelas belum dimulai"
                }
                3200->{
                    tvKeterangan.text = "Kelas sudah selesai"
                }
                4100->{
                    tvKeterangan.text = "QrCode kedaluwarsa"
                }
            }
        }

        button.setOnClickListener {
            alert.dismiss()
        }

        builder.setView(parentDialog)
        alert = builder.create()
        alert.show()

    }



    private fun scanQrCode() {
        if (hasNoPermision()) {
            requestPermission()
        } else {
            startActivity(Intent(this, QrCodeActivity::class.java))
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

    private fun hasNoPermision(): Boolean {
        return ContextCompat.checkSelfPermission(
            this@MainActivity,
            android.Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, permission, 0)
    }

    companion object {
        const val MYPREF = "SharedPreference"
        const val TOKEN_LOGIN = "tokenLogin"
        const val LOG_D = "LOG_D"
        const val DEFAULT = "iii"

    }
}

