package com.nexvis.nexretail.view.adapter

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.nexvis.nexretail.R
import kotlinx.android.synthetic.main.list_food_option.view.*
import kotlinx.android.synthetic.main.list_foods_order.view.*

class OrderDetailAdapter (val context: Context): RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>()  {

    private var listItems = arrayListOf("Chha Kdav","Soup")

    class OrderDetailViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun onBind(listItems: ArrayList<kotlin.String>, position: Int, context: Context) {
            itemView.txt_food_name.text = listItems[position]
            setUpFoodRcy(itemView, context)

        }

        private fun setUpFoodRcy(itemView: View, context: Context) {
            val adapter = FoodOptionAdapter( context)
            itemView.rcy_food_option.layoutManager = LinearLayoutManager(context)
            itemView.rcy_food_option.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_foods_order, parent, false)
        return OrderDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.onBind(listItems, position, context)
    }

    override fun getItemCount(): Int {
       return listItems.size
    }

}



class FoodOptionAdapter(context: Context) : RecyclerView.Adapter<FoodOptionAdapter.FoodOptionViewHolder>(){

    private var listFoods = arrayListOf("P1","P2")

    class FoodOptionViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun onBind(listFoods: ArrayList<String>, position: Int){
            itemView.txt_option_name.text = listFoods[position]
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodOptionViewHolder {
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.list_food_option, parent, false)
        return FoodOptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodOptionViewHolder, position: Int) {
        holder.onBind(listFoods,position)
    }

    override fun getItemCount(): Int {
        return listFoods.size
    }


}






