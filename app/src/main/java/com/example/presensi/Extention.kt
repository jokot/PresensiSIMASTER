package com.example.presensi

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.saveToken(token:String){
    val editor = getSharedPreferences(MainActivity.MYPREF,Context.MODE_PRIVATE).edit()
    editor.putString(MainActivity.TOKEN_LOGIN,token)
    editor.apply()
}

fun Context.logout(){
    val editor  = getSharedPreferences(MainActivity.MYPREF,Context.MODE_PRIVATE).edit()
    editor.clear()
    editor.apply()
}

fun Context.getToken():String{
    val pref = getSharedPreferences(MainActivity.MYPREF,Context.MODE_PRIVATE)
    return pref.getString(MainActivity.TOKEN_LOGIN,MainActivity.DEFAULT)
}

fun Context.toast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun Context.logD(message:String){
    Log.d(MainActivity.LOG_D,message)
}