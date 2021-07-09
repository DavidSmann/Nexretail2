package com.nexvis.nexretail.view.adapter

    import android.content.ContentValues.TAG
    import android.content.Context
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.view.animation.Animation
    import android.view.animation.AnimationUtils
    import android.view.animation.LinearInterpolator
    import androidx.core.content.ContextCompat
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    import com.android.volley.VolleyError
    import com.google.gson.GsonBuilder
    import com.nexvis.nexretail.R
    import com.nexvis.nexretail.custom.ErrorHandleView
    import com.nexvis.nexretail.request.NexRetailJSONObjectRequest
    import org.json.JSONObject
    import pl.droidsonroids.gif.GifImageView


    abstract class BaseAdapter<in V : RecyclerView.ViewHolder, T>(
        private val context: Context,
        private val errorHandleView: ErrorHandleView,
        private val recyclerView: RecyclerView?,
        private val swipeRefreshLayout: SwipeRefreshLayout?
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var offset = 0
        var limit = 20

        private var isLoadingData: Boolean = false
        private var mOnDataResponse: OnDataResponse? = null
        private var mOnDataErrorResponse: OnDataErrorResponse? = null

        protected val mGson = GsonBuilder().create()

        protected open val noDataMessage: String? = null

        protected open val countNoData: Int = 1

        protected open val retryRequestError: Boolean = false

        /**
         * if your adapter need load more data you need to call this function to true when data response < getLimit()
         *
         * @param done if true not load data anymore
         */
        var isDone: Boolean = false

        abstract val urlRequestFunctionName: String
        abstract val mItems :List<T>

        constructor(context: Context, errorHandleView: ErrorHandleView) : this(context, errorHandleView, null, null)

        init {

            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context, R.color.green))
                swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
                    offset = 0
                    //mItems.clear()
                    onPullRefresh()
                    notifyDataSetChanged()
                    onRequestData(mOnDataResponse)
                    swipeRefreshLayout.isRefreshing = false
                })
            }
            recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisibleItem: Int = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if (!isLoadingData && lastVisibleItem > itemCount - 5 && !isDone && itemCount > 1) {
                        offset = itemCount - 1
                        onRequestData(mOnDataResponse)
                    }
                }
            })
        }

        fun onReloadData() {
            offset = 0
            onRequestData(mOnDataResponse, mOnDataErrorResponse)
        }

        /**
         * call this function need to override getUrlRequestFunctionName()
         */
        fun onRequestData(onDataResponse: OnDataResponse?, onDataErrorResponse: OnDataErrorResponse? = null) {
            mOnDataResponse = onDataResponse
            mOnDataErrorResponse = onDataErrorResponse
            isDone = false
            isLoadingData = true
            if (itemCount == 1) errorHandleView.setViewMode(ErrorHandleView.Mode.LOADING)
            object : NexRetailJSONObjectRequest(context) {

                override fun getFunctionName(): String {
                    return urlRequestFunctionName
                }

                override fun onGetUrlParams(requestParams: RequestParams) {
                    // requestParams.put("limit", limit)
                    // requestParams.put("offset", offset)
                    val tmpParams = HashMap<String, String>()
                    getRequestParams(tmpParams)
                    for (key in tmpParams.keys) {
                        requestParams.put(key, tmpParams[key])
                    }
                    super.onGetUrlParams(requestParams)
                }
            }.setOnErrorListener { error ->
                isLoadingData = false
                isDone = true
                if (recyclerView != null) {
                    recyclerView.visibility = View.INVISIBLE
                }
                when {
                    onDataErrorResponse != null -> onDataErrorResponse.onError(error)
//                error is NoConnectionError -> errorHandleView.setViewMode(ErrorHandleViewV2.Mode.NO_INTERNET)
//                error is TimeoutError -> errorHandleView.setViewMode(ErrorHandleViewV2.Mode.TIME_OUT)
                    else -> {
//                    errorHandleView.visibility = View.GONE
//                    VIPAlertDialog.with(context, getErrorMessageFrom(error)).show()
                    }
                }
            }.execute { response ->
                (response as JSONObject)
                Log.d(TAG, "onRequestData: "+ response)
                isLoadingData = false
                mOnDataResponse?.onResponse(response)
                isDone = if (response.has("data"))
                    response.optJSONArray("data").length() < limit
                else true

                if (itemCount - countNoData == 0 && isDone) {
                    errorHandleView.setViewMode(ErrorHandleView.Mode.NO_DATA, noDataMessage)
                    onNoData()
                } else {
                    if (recyclerView != null) {
                        recyclerView.visibility = View.VISIBLE
                    }
                    errorHandleView.visibility = View.GONE
                }
            }
        }

        open fun onNoData() {}

        open fun onPullRefresh() {}

        abstract fun getRequestParams(params: HashMap<String, String>)

        abstract fun onBind(viewHolder: V, position: Int)

        abstract fun getItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

        override fun getItemViewType(position: Int): Int {
            return if (position == mItems.size) LOAD_MORE_ITEM_TYPE else position
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType == LOAD_MORE_ITEM_TYPE) {
                LoadMoreViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_load_more, parent, false))
            } else getItemViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
            if (p0 !is BaseAdapter<*, *>.LoadMoreViewHolder) onBind(p0 as V, p1)
            else p0.onBind()
        }

        override fun getItemCount(): Int = mItems.size + 1

        inner class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var loading: GifImageView = itemView.findViewById(R.id.image_view_progress)
            var anim: Animation = AnimationUtils.loadAnimation(itemView.context, R.anim.rotate)

            init {
                anim.interpolator = LinearInterpolator()
            }

            fun onBind() {
                loading.startAnimation(anim)
                if (isDone || itemCount == 1) loading.visibility = View.GONE
                else loading.visibility = View.VISIBLE
            }
        }

        companion object {
            private const val LOAD_MORE_ITEM_TYPE = -1
        }


        interface OnDataResponse {
            fun onResponse(onResponse: JSONObject)
        }

        interface OnDataErrorResponse {
            fun onError(error: VolleyError)
        }
    }
