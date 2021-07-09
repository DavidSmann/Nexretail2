package com.nexvis.nexretail.view.adapter

import android.content.Context
import android.view.*

import androidx.recyclerview.widget.RecyclerView
import com.nexvis.nexretail.R
import kotlinx.android.synthetic.main.list_booking.view.*
import kotlinx.android.synthetic.main.list_recent_activity.view.*


class RecentActivityAdapter (val context: Context): RecyclerView.Adapter<RecentActivityAdapter.BookingViewHolder>()  {

    private var listItems = arrayListOf("Room A is Check in","Room B is Check in")

    class BookingViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun onBind(listItems: ArrayList<kotlin.String>, position: Int, context: Context) {
            itemView.txt_action.text = listItems[position]
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_recent_activity, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        holder.onBind(listItems, position, context)
    }

    override fun getItemCount(): Int {
       return listItems.size
    }
}






