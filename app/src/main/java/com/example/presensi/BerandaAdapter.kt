package com.example.presensi


//import android.support.v7.widget.RecyclerView
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presensi.Api.MatkulResponse
import kotlinx.android.synthetic.main.item_view_b.view.*


class BerandaAdapter(
    private var curretTime: Int,
    private var curretDay: Int,
    private var selectedDay: Int,
    private var list: MutableList<MatkulResponse>
) : RecyclerView.Adapter<BerandaAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(group: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(group.context).inflate(R.layout.item_view_b, group, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, potition: Int) {
        holder.bindItem(list[potition])
    }


    inner class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: MatkulResponse) {
            itemView.tv_matkul.text = "${item.subject.kode} - ${item.subject.name}"
            itemView.tv_time.text = "${item.startTime} - ${item.endTime}"
            itemView.tv_ruang.text = item.ruang.name
            itemView.tv_lecturer.text = item.lecturer.name

            if (curretDay == selectedDay) {
                val mTimeS = item.startTime
                val listMTimeS = mTimeS.split(":")
                val jamS = (listMTimeS[0].toInt() * 60)
                val menitS = listMTimeS[1].toInt()
                val mTimeMS = jamS + menitS

                val mTimeE = item.endTime
                val listMTimeE = mTimeE.split(":")
                val jamE = (listMTimeE[0].toInt() * 60)
                val menitE = listMTimeE[1].toInt()
                val mTimeME = jamE + menitE

                val hadir = item.userAttend
                if (hadir) {
                    itemView.tv_attend.visibility = View.VISIBLE
                }

                if (curretTime < mTimeMS) {
                    itemView.tv_attend.visibility = View.GONE
                    val jam = (mTimeMS - curretTime) / 60
                    val menit = (mTimeMS - curretTime) % 60
                    if (jam > 0) {
                        itemView.tv_current_time.text = "$jam jam $menit menit lagi"
                    } else {
                        itemView.tv_current_time.text = "$menit menit lagi"
                    }
                } else if (curretTime < mTimeME) {
                    itemView.tv_current_time.text = "Sedang berlangsung"
                    itemView.tv_current_time.setTextColor(Color.parseColor("#2DDE6A"))
                } else {
                    itemView.tv_current_time.text = "Selesai"
                    itemView.tv_current_time.setTextColor(Color.parseColor("#4192FC"))
                }
            } else if (curretDay < selectedDay) {
                itemView.tv_current_time.text = "${selectedDay - curretDay} hari lagi"
            } else {
                itemView.tv_current_time.visibility = View.INVISIBLE
            }

        }
    }
}