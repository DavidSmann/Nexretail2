package com.nexvis.nexretail.interactor

import com.danny.coremodule.CorePresenter
import com.danny.coremodule.CoreView
import org.json.JSONObject

interface BookNowInteractor {

    interface View : CoreView {
        fun onBookNowResponse(data: JSONObject)
        fun onBookNowFailed(message: String)
    }

    interface Presenter : CorePresenter<View> {
        fun onBookNow(body: JSONObject)
    }
}