package com.nexvis.nexretail.presenter

import com.danny.coremodule.CoreImpl
import com.nexvis.nexretail.interactor.OrderItemInteractor
import org.json.JSONObject

class OrderItemPresenter: CoreImpl<OrderItemInteractor.View>(), OrderItemInteractor.Presenter {

    override fun onOrderItem(body: JSONObject) {
        TODO("Not yet implemented")
    }
}