package com.example.drugassignment.Profile_Module.sub_module

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.Class.DrugDetail
import com.example.drugassignment.Class.OtherUser
import com.example.drugassignment.Class.SubUser
import com.example.drugassignment.FIreStore.FirestoreAdapter
import com.example.drugassignment.Information_Module.DrugDetailAdapter
import com.example.drugassignment.Information_Module.Information_MainDirections
import com.example.drugassignment.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.infor_layout_main.view.*
import kotlinx.android.synthetic.main.member_view_item.view.*


open class MemberAdapter2(query: Query?, private val mListener: OnRestaurantSelectedListener) : FirestoreAdapter<MemberAdapter2.ViewHolder?>(query) {

    interface OnRestaurantSelectedListener {
        fun onRestaurantSelected(restaurant: DocumentSnapshot?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        Log.i("testing", "123")
        return ViewHolder(inflater.inflate(R.layout.member_view_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getSnapshot(position), mListener, position)
        Log.i("testing", position.toString())
//        val User = getSnapshot(position).toObject(SubUser::class.java)
//
//        holder.itemView.setOnClickListener {
            //                        Toast.makeText(
//                it.context,
//                position.toString() + "",
//                Toast.LENGTH_SHORT
//            ).show()
////            it.findNavController().navigate(R.id.action_information_Main_to_drugInfo)
////            it.findNavController().navigate(
////                Information_MainDirections
////                    .actionInformationMainToDrugInfo
////                        (User!!.drugName?:"123", drug.drugInfo?:"null", drug.drugSideEffect?:"null"))
//
//        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(snapshot: DocumentSnapshot, listener: OnRestaurantSelectedListener?, position: Int) {
            val user = snapshot.toObject(OtherUser::class.java)

            Log.i("testing",user?.email )

            itemView.txtEmail.text = user?.email
            itemView.txtName.text = user?.displayName
            itemView.txtAddress.text = user?.city


            // Click listener
            itemView.setOnClickListener { listener?.onRestaurantSelected(snapshot) }
        }
    }
}