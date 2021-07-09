package com.nexvis.nexretail.interactor

import com.danny.coremodule.CorePresenter
import com.danny.coremodule.CoreView
import org.json.JSONObject

interface OpenBillInteractor {
    interface View : CoreView {
        fun onOpenBillResponse(data: JSONObject)
        fun onOpenBillFailed(message: String)
    }

    interface Presenter : CorePresenter<View> {
        fun onOpenBill(body: JSONObject)
    }
}