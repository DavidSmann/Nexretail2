package com.nexvis.nexretail.view.adapter

import android.content.Context
import android.view.*

import androidx.recyclerview.widget.RecyclerView
import com.nexvis.nexretail.R
import kotlinx.android.synthetic.main.list_booking.view.*


class OpenBillAdapter (val context: Context): RecyclerView.Adapter<OpenBillAdapter.OpenBillViewHolder>()  {

    private var listItems = arrayListOf("00001","00002")

    class OpenBillViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun onBind(listItems: ArrayList<kotlin.String>, position: Int, context: Context) {
            itemView.txt_booking_number.text = listItems[position]
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenBillViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_booking, parent, false)
        return OpenBillViewHolder(view)
    }

    override fun onBindViewHolder(holder: OpenBillViewHolder, position: Int) {
        holder.onBind(listItems, position, context)
    }

    override fun getItemCount(): Int {
       return listItems.size
    }
}






