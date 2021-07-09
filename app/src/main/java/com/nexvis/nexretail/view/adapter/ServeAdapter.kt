package com.nexvis.nexretail.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexvis.nexretail.R
import kotlinx.android.synthetic.main.list_booking.view.*

class ServeAdapter (val context: Context): RecyclerView.Adapter<ServeAdapter.BookingViewHolder>()  {

    private var listItems = arrayListOf("00001","00002")

    class BookingViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun onBind(listItems: ArrayList<kotlin.String>, position: Int, context: Context) {
            itemView.txt_booking_number.text = listItems[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_booking, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        holder.onBind(listItems, position, context)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}
