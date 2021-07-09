package com.nexvis.nexretail.interactor

import com.danny.coremodule.CorePresenter
import com.danny.coremodule.CoreView
import org.json.JSONObject

interface KitchenInteractor {
    interface View : CoreView {
        fun onKitchenResponse(data: JSONObject)
        fun onKitchenFailed(message: String)
    }

    interface Presenter : CorePresenter<View> {
        fun onKitchen(body: JSONObject)
    }
}