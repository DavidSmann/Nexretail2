package com.nexvis.nexretail.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nexvis.nexretail.utils.inflate

abstract class BaseFragment : Fragment() {

    private lateinit var mContext: Context
    private var mLayoutId: Int = 0
    private lateinit var firebaseToken: String
//    private val mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getFragmentLayoutId(),container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewCreated()
    }

    abstract fun onViewCreated()

    abstract fun getFragmentLayoutId(): Int

    fun getmContext(): Context {
        return mContext
    }

    fun getmFragment(): BaseFragment {
        return this
    }

    fun hideSwipeRefreshLayout(context: SwipeRefreshLayout, status: Boolean) {
        context.isRefreshing = status

    }

    fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()

    fun String.pluralize(count: Int, plural: String? = null): String? {
        return if (count > 1 || count == 0) {
            plural ?: this + 's'
        } else {
            this
        }
    }


}