package com.example.presensi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view_n.view.*

class NotifAdapter(private var list: MutableList<String>):
    RecyclerView.Adapter<NotifAdapter.NotifViewHolder>(){
    override fun onCreateViewHolder(group: ViewGroup, viewType: Int): NotifAdapter.NotifViewHolder {
        return NotifViewHolder(
            LayoutInflater.from(group.context).inflate(R.layout.item_view_n,group,false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NotifAdapter.NotifViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    inner class NotifViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bindItem(item:String){
            itemView.tv_info.text = item
            if (item == "Anda tidak hadir pada kelas MII4513 - Manajemen Proyek Teknologi Informasi"){
                itemView.iv_status.setImageResource(R.drawable.ic_user_uncheck)
            }
        }
    }
}