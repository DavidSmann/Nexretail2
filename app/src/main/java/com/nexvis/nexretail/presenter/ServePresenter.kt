package com.nexvis.nexretail.presenter

import com.danny.coremodule.CoreImpl
import com.nexvis.nexretail.interactor.ServeInteractor
import org.json.JSONObject

class ServePresenter : CoreImpl<ServeInteractor.View>(),
    ServeInteractor.Presenter {
    override fun onServe(body: JSONObject) {
        TODO("Not yet implemented")
    }
}