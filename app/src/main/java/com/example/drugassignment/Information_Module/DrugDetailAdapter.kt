package com.example.drugassignment.Information_Module

import android.app.DownloadManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.Class.DrugDetail
import com.example.drugassignment.FIreStore.FirestoreAdapter
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.R
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.infor_layout_main.view.*
import androidx.fragment.app.FragmentActivity




open class DrugDetailAdapter(query: Query?, private val mListener: OnRestaurantSelectedListener) : FirestoreAdapter<DrugDetailAdapter.ViewHolder?>(query) {

    interface OnRestaurantSelectedListener {
        fun onRestaurantSelected(restaurant: DocumentSnapshot?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.infor_layout_main, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getSnapshot(position), mListener, position)
        val drug = getSnapshot(position).toObject(DrugDetail::class.java)


        holder.itemView.setOnClickListener {
            it.findNavController().navigate(Information_MainDirections
                .actionInformationMainToDrugInfo
                    (drug!!.drugName?:"123", drug.drugInfo?:"null", drug.drugSideEffect?:"null"))

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(snapshot: DocumentSnapshot, listener: OnRestaurantSelectedListener?, position: Int) {
            val drug = snapshot.toObject(DrugDetail::class.java)

            itemView.InfoMain.text = (position + 1) .toString() + "        "+ drug?.drugName

            // Click listener
            itemView.setOnClickListener { listener?.onRestaurantSelected(snapshot) }
        }
    }
}