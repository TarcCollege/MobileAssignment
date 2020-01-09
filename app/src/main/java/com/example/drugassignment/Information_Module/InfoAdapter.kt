package com.example.drugassignment.Information_Module

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentInformationMainBinding


class InfoAdapter: RecyclerView.Adapter<InfoAdapter.ViewHolder>() {
    var data =  listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view : View = layoutInflater
            .inflate(com.example.drugassignment.R.layout.infor_layout_main, parent, false) as View

        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        //Log.i("size", data.size.toString())
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.infoMain.text = data[position]
        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_information_Main_to_drugInfo)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
       // val index: TextView = itemView.findViewById(com.example.drugassignment.R.id.infoIndex)
        val infoMain : TextView = itemView.findViewById(com.example.drugassignment.R.id.InfoMain)
        val ItemHolder : LinearLayout = itemView.findViewById(com.example.drugassignment.R.id.ItemHolder)

    }


}