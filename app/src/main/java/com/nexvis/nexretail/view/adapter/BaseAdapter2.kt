package com.nexvis.nexretail.view.adapter

import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.nexvis.nexretail.R

abstract class BaseAdapter2<V : RecyclerView.ViewHolder> : RecyclerView.Adapter<V>() {
    private var lastPosition = -1
    private var delay: Handler? = null
    fun setAnimation(itemView: View, position: Int) {
        delay = Handler()
        delay?.postDelayed({
            if (position > lastPosition) {
                itemView.visibility = View.VISIBLE
                val animation = AnimationUtils.loadAnimation(itemView.context, R.anim.anim_recycler_item) as Animation
                itemView.startAnimation(animation)
                lastPosition = position
            }
            delay?.removeCallbacksAndMessages(this)
            delay = null

        }, 500+(position*20).toLong())
    }

    fun getLastPosition(): Int {
        return lastPosition
    }
}