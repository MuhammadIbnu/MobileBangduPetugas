package mibnu.team.petugas.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_data_masuk.view.*
import mibnu.team.petugas.R
import mibnu.team.petugas.view.DetailDataBaruActivity
import mibnu.team.petugas.models.Data

class DataAdapter (private var datas : MutableList<Data>, private var context: Context): RecyclerView.Adapter<DataAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_data_masuk, parent, false))

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(datas[position], context)

    fun updateList(ls : List<Data>){
        datas.clear()
        datas.addAll(ls)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(d : Data, context: Context){
            with(itemView){
                tv_nik.text = d.waris?.nik.toString()
                tv_name.text = d.waris!!.nama
                setOnClickListener {
                    context.startActivity(Intent(context, DetailDataBaruActivity::class.java).apply {
                        putExtra("DATA", d)
                    })
                }
            }
        }
    }
}