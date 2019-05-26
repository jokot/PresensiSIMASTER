package com.example.presensi


import android.annotation.SuppressLint
import android.annotation.TargetApi

import android.os.Build
import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_kehadiran.*
import kotlinx.android.synthetic.main.fragment_kehadiran.view.*

//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// *
// */
class KehadiranFragment : Fragment() {
    private val list = mutableListOf("MII3007 - Filsafat Ilmu Komputer",
        "MII4411 - Pengenalan Pola",
        "MII3005 - Penulisan Karya Ilmiah",
        "MII3009 - Enterpreunership dan Su...",
        "MII4513 - Manajemen Proyek Tekn...",
        "asd",
        "asdf"
        )
    private lateinit var adapter: KehadiranAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kehadiran, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.ll_dd.setOnClickListener {
            showDropDown()
        }
        initRecycler()
    }


    @TargetApi(Build.VERSION_CODES.M)
    private fun showDropDown(){
        val showMenu = view?.findViewById<LinearLayout>(R.id.ll_dd)
        showMenu?.setOnClickListener {
            val dropDownMenu = PopupMenu(context!!, showMenu)
            dropDownMenu.menuInflater.inflate(R.menu.drop_down_menu, dropDownMenu.menu)
            dropDownMenu.setOnMenuItemClickListener { menuItem ->
                tv_dd.text = menuItem.title
                true
            }
            dropDownMenu.show()
        }
    }

    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.M)
    private fun initRecycler(){
        adapter = KehadiranAdapter(list)
        rv_kehadiran.adapter = adapter
        rv_kehadiran.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
    }
}
