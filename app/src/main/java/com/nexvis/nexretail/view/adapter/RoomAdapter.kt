package com.nexvis.nexretail.view.adapter

import android.content.Context
import android.view.*

import androidx.recyclerview.widget.RecyclerView
import com.nexvis.nexretail.R
import kotlinx.android.synthetic.main.list_booking.view.*
import kotlinx.android.synthetic.main.list_booking.view.txt_booking_number
import kotlinx.android.synthetic.main.list_room.view.*


class RoomAdapter (val context: Context): RecyclerView.Adapter<RoomAdapter.RoomViewHolder>()  {

    private var listItems = arrayListOf("00001","00002","0003","0004","00051")

    class RoomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun onBind(listItems: ArrayList<kotlin.String>, position: Int, context: Context) {
            itemView.txt_room_type.text = listItems[position]
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_room, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.onBind(listItems, position, context)
    }

    override fun getItemCount(): Int {
       return listItems.size
    }
}






