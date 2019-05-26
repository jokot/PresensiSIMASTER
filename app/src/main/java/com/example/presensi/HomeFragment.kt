package com.example.presensi


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

import java.text.SimpleDateFormat


//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// *
// */
class HomeFragment : Fragment() {
    private lateinit var berandaAdapter: BerandaAdapter
    private val list = mutableListOf("MII3007 - Filsafat Ilmu Komputer", "MII4411 - Pengenalan Pola")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.iv_calendar.setOnClickListener {
            showDatePicker()
        }

        view.tv_date.setOnClickListener{
            showDatePicker()
        }

        initRecycler()
    }

    @SuppressLint("SimpleDateFormat")
    private fun showDatePicker(){
        val newCalendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(context,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                val newDate = Calendar.getInstance()
                newDate.set(year, monthOfYear, dayOfMonth)

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                val dateFormatter = SimpleDateFormat("EEE, d MMM yyyy")
                tv_date.text = dateFormatter.format(newDate.time)
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)
        )

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show()
    }

    private fun initRecycler() {
        berandaAdapter = BerandaAdapter(list)

        rv_home.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_home.adapter = berandaAdapter
    }
}
