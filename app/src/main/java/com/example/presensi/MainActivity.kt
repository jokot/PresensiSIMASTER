package com.example.presensi

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity(), FragmentChange {

    private val permission = arrayOf(android.Manifest.permission.CAMERA)

    override fun onFragmentChange(fragment: Fragment) {
        changeFragment(fragment)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_beranda -> {
                changeFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_presensi -> {
                if (hasNoPermision()) {
                    requestPermission()
                }else{
                    startActivity(Intent(this, CameraActivity::class.java))
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_riwayat -> {
                changeFragment(HistoryFragment())
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

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_container, HomeFragment())
            .commit()
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
}
