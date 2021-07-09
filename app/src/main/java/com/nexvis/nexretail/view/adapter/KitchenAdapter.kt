package com.nexvis.nexretail.view.adapter

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.nexvis.nexretail.R
import kotlinx.android.synthetic.main.activity_kitchen.view.*
import kotlinx.android.synthetic.main.list_booking.view.*
import kotlinx.android.synthetic.main.list_foods_kitchen.view.*
import kotlinx.android.synthetic.main.list_kitchen.view.*


class KitchenAdapter (val context: Context): RecyclerView.Adapter<KitchenAdapter.KitchenViewHolder>()  {

    private var listItems = arrayListOf("00001","00002")

    class KitchenViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun onBind(listItems: ArrayList<kotlin.String>, position: Int, context: Context) {
            itemView.txt_room_type.text = listItems[position]
            setUpFoodRcy(itemView, context)

        }

        private fun setUpFoodRcy(itemView: View, context: Context) {
            val adapter = KitchenFoodAdapter( context)
            itemView.rcy_foods_kitchen.layoutManager = LinearLayoutManager(context)
            itemView.rcy_foods_kitchen.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KitchenViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_kitchen, parent, false)
        return KitchenViewHolder(view)
    }

    override fun onBindViewHolder(holder: KitchenViewHolder, position: Int) {
        holder.onBind(listItems, position, context)
    }

    override fun getItemCount(): Int {
       return listItems.size
    }

}



class KitchenFoodAdapter(context: Context) : RecyclerView.Adapter<KitchenFoodAdapter.KitchenFoodViewHolder>(){

    private var listFoods = arrayListOf("Chha Kdav","Sach Ang")

    class KitchenFoodViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun onBind(listFoods: ArrayList<String>, position: Int){
            itemView.txt_food.text = listFoods[position]
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KitchenFoodViewHolder {
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.list_foods_kitchen, parent, false)
        return KitchenFoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: KitchenFoodViewHolder, position: Int) {
        holder.onBind(listFoods,position)
    }

    override fun getItemCount(): Int {
        return listFoods.size
    }


}






