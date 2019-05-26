package com.example.presensi

//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view_b.view.*

class KehadiranAdapter(
    private var list:MutableList<String>
) : RecyclerView.Adapter<KehadiranAdapter.KehadiranViewHolder>(){

    override fun onCreateViewHolder(group: ViewGroup, viewType: Int): KehadiranAdapter.KehadiranViewHolder {
        return KehadiranViewHolder(
            LayoutInflater.from(group.context).inflate(R.layout.item_view_k,group,false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: KehadiranAdapter.KehadiranViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    inner class KehadiranViewHolder(view:View): RecyclerView.ViewHolder(view){
        fun bindView(item:String){
            itemView.tv_matkul.text = item
        }
    }

}