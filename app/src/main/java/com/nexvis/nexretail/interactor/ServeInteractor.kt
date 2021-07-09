package com.nexvis.nexretail.interactor

import com.danny.coremodule.CorePresenter
import com.danny.coremodule.CoreView
import org.json.JSONObject

interface ServeInteractor {
    interface View : CoreView {
        fun onServeResponse(data: JSONObject)
        fun onServeFailed(message: String)
    }

    interface Presenter : CorePresenter<View> {
        fun onServe(body: JSONObject)
    }
}