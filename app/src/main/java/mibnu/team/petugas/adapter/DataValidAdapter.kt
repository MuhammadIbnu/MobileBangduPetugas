package mibnu.team.petugas.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_data_valid.view.*
//import kotlinx.android.synthetic.main.item_data_valid.view.*
import mibnu.team.petugas.R
import mibnu.team.petugas.models.Data
import mibnu.team.petugas.view.DetailDataConfirmedIIActivity


class DataValidAdapter (private  var dataValid:MutableList<Data>, private  var context: Context):RecyclerView.Adapter<DataValidAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_data_valid, parent,false))

    override fun getItemCount()=dataValid.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)= holder.bind(dataValid[position], context)

    fun updateList(ls : List<Data>){
        dataValid.clear()
        dataValid.addAll(ls)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(d : Data, context: Context){
            with(itemView){
                tv_nik.text = d.waris?.nik.toString()
                tv_name.text = d.waris!!.nama
                if (d.confirmedII==true){
                    val dataReceived : TextView = findViewById(R.id.icon)
                    val hasil ="Diterima"
                    dataReceived.text=hasil
                }else{
                    val dataReceived : TextView = findViewById(R.id.icon1)
                    val hasil ="Ditolak"
                    dataReceived.text=hasil
                }
                setOnClickListener {
                    context.startActivity(Intent(context, DetailDataConfirmedIIActivity::class.java).apply {
                        putExtra("DATA", d)
                    })
                }
            }
        }

    }

}