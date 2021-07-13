package com.nexvis.nexretail.interactor

import com.danny.coremodule.CorePresenter
import com.danny.coremodule.CoreView
import org.json.JSONObject

interface OrderItemInteractor {
    interface View : CoreView {
        fun onOrderItemResponse(data: JSONObject)
        fun onOrderItemFailed(message: String)
    }

    interface Presenter : CorePresenter<View> {
        fun onOrderItem(body: JSONObject)
    }
}