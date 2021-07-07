package com.nexvis.nexretail.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.nexvis.nexretail.R
import kotlinx.android.synthetic.main.view_error_handler.view.*

class ErrorHandleView : FrameLayout {
    private lateinit var progressBar: ProgressBar
    private lateinit var icon: ImageView
    private lateinit var retry: ImageView
    private lateinit var smg: TextView

    private var onRetry: (() -> Unit)? = null

    enum class Mode {
        LOADING,
        NO_DATA,
        TIME_OUT,
        ERROR,
        HIDE
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context) : super(context) {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_error_handler, this)
        visibility = View.INVISIBLE
        progressBar = findViewById(R.id.progressBar)
        smg = findViewById(R.id.text_msg)
        icon = findViewById(R.id.image_icon)
        retry = findViewById(R.id.image_retry)

//        anim = AnimationUtils.loadAnimation(context, R.anim.rotate)
//        anim.interpolator = LinearInterpolator()
    }

    fun setViewMode(mode: Mode, message: String? = null, retryAction: Boolean = false) {
        post {
            isClickable = false
            visibility = View.VISIBLE
            icon.visibility = View.GONE
            layout_retry.visibility = View.GONE

            when (mode) {
                Mode.LOADING -> {
                    isClickable = true
                    progressBar.visibility = View.VISIBLE
//                    progressBar.startAnimation(anim)
                    no_internet_view.visibility = View.INVISIBLE
                    smg.text = ""
                }
                Mode.NO_DATA -> {
                    progressBar.clearAnimation()
                    progressBar.visibility = View.GONE
                    no_internet_view.visibility = View.VISIBLE
                    smg.text = message ?: context.getString(R.string.no_data_available)
//                    if (retryAction) {
//                        no_internet_view.setOnClickListener {
//                            onRetry?.invoke()
//                        }
//                    }
                }
                Mode.TIME_OUT -> {
                    progressBar.clearAnimation()
                    progressBar.visibility = View.GONE
                    no_internet_view.visibility = View.VISIBLE
                    smg.text = message ?: context.getString(R.string.connection_time_out)
                    if (retryAction) {
                        layout_retry.visibility = View.VISIBLE
                        no_internet_view.setOnClickListener {
                            onRetry?.invoke()
                        }
                    }
                }

                Mode.ERROR -> {
                    progressBar.clearAnimation()
                    progressBar.visibility = View.GONE
                    layout_retry.visibility = View.VISIBLE
                    no_internet_view.visibility = View.VISIBLE
                    icon.visibility = View.VISIBLE
                    icon.setImageResource(R.drawable.ic_error_outline_24)
                    smg.text = message ?: context.getString(R.string.there_is_an_error_occurred)
                    no_internet_view.setOnClickListener {
                        onRetry?.invoke()
                    }
                }
                Mode.HIDE -> {
                    progressBar.visibility = View.GONE
                    progressBar.clearAnimation()
                    visibility = View.GONE
                }
            }
        }
    }

    fun setOnRetryListener(onRetry: (() -> Unit)? = null) {
        this.onRetry = onRetry
    }
}
