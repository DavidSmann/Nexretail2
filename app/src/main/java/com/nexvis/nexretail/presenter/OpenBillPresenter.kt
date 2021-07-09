package com.nexvis.nexretail.presenter

import com.danny.coremodule.CoreImpl
import com.nexvis.nexretail.interactor.OpenBillInteractor
import org.json.JSONObject

class OpenBillPresenter :CoreImpl<OpenBillInteractor.View>(),
        OpenBillInteractor.Presenter {
    override fun onOpenBill(body: JSONObject) {
        TODO("Not yet implemented")
    }
}