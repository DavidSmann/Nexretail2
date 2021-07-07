package com.nexvis.nexretail.custom

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexvis.nexretail.R
import com.nexvis.nexretail.model.NavigationModel
import com.nexvis.nexretail.utils.Constant
import com.nexvis.nexretail.view.adapter.NavigationDrawerAdapter
import kotlinx.android.synthetic.main.view_drawer.view.*

class DrawerView : RelativeLayout {

    private lateinit var mOnNavigationListener: OnNavigationListener
    private var mEarnPoint: Double = 0.0
    private var adapterMenu: NavigationDrawerAdapter? = null

    @RequiresApi(Build.VERSION_CODES.M)
    constructor(context: Context) : this(context, null)

    @RequiresApi(Build.VERSION_CODES.M)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.M)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_drawer, this)

        recycler_view_navigation.apply {
            layoutManager = LinearLayoutManager(context)
            adapterMenu = NavigationDrawerAdapter(context, getNavigationModels()) {
                mOnNavigationListener?.onItemClick(it)
                when (it) {
                    Constant.DataTypeMenu.BOOKNOW -> {
                        Log.d("CLick","Click=== BOOKNOW")
                    }
                    Constant.DataTypeMenu.SERVENOW -> {
                        Log.d("CLick","Click=== SERVENOW")
                    }
                    Constant.DataTypeMenu.ORDERHISTORY -> {
                        Log.d("CLick","Click=== ORDERHISTORY")
                    }
                    Constant.DataTypeMenu.PROMOTION -> {
                        Log.d("CLick","Click=== PROMOTION")
                    }
                    Constant.DataTypeMenu.SETTING -> {
                        Log.d("CLick","Click=== SETTING")
                    }

                }
            }
            adapter = adapterMenu
        }

        // Move content touch able block of profile by scroll
        recycler_view_navigation.setOnScrollChangeListener { _, _, _, _, _ ->
            val offsetY = recycler_view_navigation.computeVerticalScrollOffset()
            view_block_profile.translationY = -offsetY.toFloat()
        }
    }

    fun setNavigationListener(onNavigationListener: OnNavigationListener) {
        mOnNavigationListener = onNavigationListener
    }

    fun reloadDataMenu() {
        adapterMenu?.setData(getNavigationModels())
    }

    private fun getNavigationModels(): ArrayList<NavigationModel> {
        val navigationModels = ArrayList<NavigationModel>()

        navigationModels.add(NavigationModel(R.drawable.ic_book_now, context.getString(R.string.book_now), Constant.DataTypeMenu.BOOKNOW))
        navigationModels.add(NavigationModel(R.drawable.ic_serve_now, context.getString(R.string.serve_now), Constant.DataTypeMenu.SERVENOW))
        navigationModels.add(NavigationModel(R.drawable.ic_promotion, context.getString(R.string.promotion), Constant.DataTypeMenu.PROMOTION))

        navigationModels.add(NavigationModel(R.drawable.ic_order_history, context.getString(R.string.order_history), Constant.DataTypeMenu.ORDERHISTORY))
        navigationModels.add(NavigationModel(R.drawable.ic_setting, context.getString(R.string.settings), Constant.DataTypeMenu.SETTING))
        navigationModels.add(NavigationModel(R.drawable.ic_logout, context.getString(R.string.logout), Constant.DataTypeMenu.LOGOUT))
        return navigationModels
    }



    interface OnNavigationListener {
        fun onItemClick(type: Constant.DataTypeMenu)
    }
}