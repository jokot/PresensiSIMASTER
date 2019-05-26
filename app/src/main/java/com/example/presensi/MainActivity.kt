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
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), FragmentChange, View.OnClickListener {


    private var token : String? = null
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

        token = intent.getStringExtra("token")

        if (token != null){
            showDialog("success")
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

    private fun absen(){

    }

    private fun showDialog(status:String){
        val layoutInflater = LayoutInflater.from(this)
        val parentDialog = layoutInflater.inflate(R.layout.custom_dialog,null)

        val tvStatus = parentDialog.findViewById<TextView>(R.id.tv_status)
        val ivStatus = parentDialog.findViewById<ImageView>(R.id.iv_status)
        val tvKeterangan = parentDialog.findViewById<TextView>(R.id.tv_keterangan)
        val tvMatkul = parentDialog.findViewById<TextView>(R.id.tv_matkul)
        val button = parentDialog.findViewById<Button>(R.id.btn_dialog)

        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        var alert : AlertDialog = builder.create()

        if(status == "success"){
            tvMatkul.text = token
        }else{
            tvStatus.text = "Gagal"
            tvStatus.setTextColor(ContextCompat.getColor(this,R.color.red))
            ivStatus.visibility = View.GONE
            tvKeterangan.text = "Anda gagal melakukan presensi"
            tvMatkul.text = "Silakan coba lagi"
        }
        button.setOnClickListener{
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

    companion object{
        const val MYPREF = "SharedPreference"
        const val TOKEN_LOGIN = "tokenLogin"
        const val LOG_D = "LOG_D"
        const val DEFAULT = "iii"

    }
}

