package com.example.presensi

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_notifi.*


class NotifiFragment : Fragment() {
    private val list = mutableListOf("Anda berhasil presensi pada kelas MII3007 - Filsafat Ilmu Komputer",
        "Anda tidak hadir pada kelas MII4513 - Manajemen Proyek Teknologi Informasi"
        )

    private lateinit var adapter: NotifAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    @SuppressLint("WrongConstant")
    private fun initRecycler(){
        adapter= NotifAdapter(list)

        rv_notif.adapter = adapter
        rv_notif.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
    }
}
