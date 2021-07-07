package com.nexvis.sunfixretail.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.nexvis.nexretail.R
import kotlinx.android.synthetic.main.view_drawer_menu.view.*

class DrawerMenuView : RelativeLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        LayoutInflater.from(context).inflate(R.layout.view_drawer_menu, this)
        val typeArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.DrawerMenuView, 0, 0)
        try {
            val title = typeArray.getString(R.styleable.DrawerMenuView_title)
            val iconType = typeArray.getDrawable(R.styleable.DrawerMenuView_displayIcon)
            val hideLine = typeArray.getBoolean(R.styleable.DrawerMenuView_hideLine, false);
            if (title?.isNotEmpty() == true) {
                tv_title.text = title
            }
            if (iconType != null) {
                img_icon_menu.setImageDrawable(iconType)
            }
            if (hideLine) view_line.visibility = View.GONE
        } finally {
            typeArray.recycle()
        }
    }
}