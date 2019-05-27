package com.example.presensi


//import android.support.v4.app.Fragment
//import android.support.v7.widget.LinearLayoutManager

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presensi.Api.MatkulResponse
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
    private lateinit var berandaAdapter: BerandaAdapter
    private val list = mutableListOf<MatkulResponse>()
    private val app = MainApp()
    private val service = app.service
    private var selectedDay = getCurrentDay()

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

        view.tv_date.setOnClickListener {
            showDatePicker()
        }
        getCurentDate()

        getMatkul(getCurrentDay())
    }

    @SuppressLint("SimpleDateFormat")
    private fun showDatePicker() {
        val newCalendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
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

                val day = SimpleDateFormat("EEE")
                selectedDay = dayToId(day.format(newDate.time))

                tv_date.text = dateFormatter.format(newDate.time)

                getMatkul(dayToId(day.format(newDate.time)))
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)
        )

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show()
    }

    private fun dayToId(day: String): Int {
        var id = 0
        when (day) {
            "Sun" -> id = 7
            "Mon" -> id = 1
            "Tue" -> id = 2
            "Wed" -> id = 3
            "Thu" -> id = 4
            "Fri" -> id = 5
            "Sat" -> id = 6
        }
        return id
    }

    private fun getCurentDate() {
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("EEE, d MMM yyyy")
        val formattedDate = df.format(c)
        tv_date.text = formattedDate
    }
    private fun getCurrentDay():Int{
        val c = Calendar.getInstance().time
        val day = SimpleDateFormat("EEE")
        val formattedDay = day.format(c)
        return  dayToId(formattedDay)
    }

    private fun getCurrentTime():Int{
        val c = Calendar.getInstance().time
        val day = SimpleDateFormat("HH:mm")
        val formattedTime = day.format(c)
        val listTime = formattedTime.split(":")
        val jam = ((listTime[0].toInt())*60)
        val menit = listTime[1].toInt()
        activity?.logD(jam.toString())
        activity?.logD(menit.toString())
        return (jam+menit)
    }

    private fun logoutDatabase() {
        activity?.logout()
        startActivity(Intent(context,LoginActivity::class.java))
        activity?.finishAffinity()
    }


    private fun getMatkul(id: Int) {
        initRecycler()
        progress_bar.visibility = View.VISIBLE
        val tokenLogin = "Bearer ${activity?.getToken()}"
        val call = service.getMatkul(tokenLogin, id.toString())
        call.enqueue(object : Callback<List<MatkulResponse>> {
            override fun onFailure(call: Call<List<MatkulResponse>>, t: Throwable) {
                activity?.progress_bar?.visibility = View.GONE
                activity?.toast(t.message.toString())
            }

            override fun onResponse(call: Call<List<MatkulResponse>>, response: Response<List<MatkulResponse>>) {
                response.let { responseMatkul ->
                    if (responseMatkul.isSuccessful) responseMatkul.body().let {
                        list.clear()
                        if (it != null) {
                            list.addAll(it)
                        }

                        berandaAdapter.notifyDataSetChanged()
                        activity?.progress_bar?.visibility = View.GONE

                    } else responseMatkul.errorBody()!!.string().let {
                        if (it == "{\"message\":\"Unauthenticated.\"}") {
                            activity?.progress_bar?.visibility = View.GONE
                            activity?.toast("Sesion Anda sudah habis, silakan login")
                            logoutDatabase()
                        }else{
                            activity?.toast("Gagal menampilkan mata kuliah")
                            activity?.progress_bar?.visibility = View.GONE
                            activity?.toast(it)
                        }
                    }

                }

            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun initRecycler() {

        berandaAdapter = BerandaAdapter(getCurrentTime(),getCurrentDay(),selectedDay,list)

        rv_home.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_home.adapter = berandaAdapter
    }
}

