package com.nexvis.nexretail.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nexvis.nexretail.R
import com.nexvis.nexretail.model.NavigationModel
import com.nexvis.nexretail.utils.Constant
import kotlinx.android.synthetic.main.view_drawer_menu.view.*


open class NavigationDrawerAdapter(private val mContext: Context, private var navigationModel: List<NavigationModel>,
                                   private val rowClick: (index: Constant.DataTypeMenu) -> Unit)
    : BaseAdapter2<NavigationDrawerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_drawer_menu, parent, false))
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.tv_title.text = navigationModel[position].title
        Glide.with(mContext).load(navigationModel[position].imageUrl).into(holder.itemView.img_icon_menu)

        holder.itemView.menuItemRootLayout.setOnClickListener {
            rowClick(navigationModel[position].gettypeRow()!!)
        }
    }

    override fun getItemCount(): Int {
        return navigationModel.size
    }

    fun setData(data: List<NavigationModel>) {
        navigationModel = data
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}


