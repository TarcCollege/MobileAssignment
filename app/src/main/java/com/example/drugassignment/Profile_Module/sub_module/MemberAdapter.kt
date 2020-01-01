package com.example.drugassignment.Profile_Module.sub_module

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.Class.OtherUser
import com.example.drugassignment.R


class MemberAdapter: RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    var data =  listOf<OtherUser>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view : View = layoutInflater
            .inflate(R.layout.member_view_item, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : OtherUser = data[position]

        holder.sleepLength.text = item.displayName + item.city + item.email + item.progression
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sleepLength: TextView = itemView.findViewById(R.id.textViewInfo)

    }


}