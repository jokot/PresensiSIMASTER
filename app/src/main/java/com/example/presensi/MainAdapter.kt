package com.example.presensi


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_view.view.*


class MainAdapter(
    private var list: MutableList<String>
) : RecyclerView.Adapter<MainAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(group: ViewGroup, viewType: Int): MainAdapter.HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(group.context).inflate(R.layout.item_view, group, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, potition: Int) {
        holder.bindItem(list[potition])
    }


    inner class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: String) {
            itemView.tv_matkul.text = item
        }
    }
}