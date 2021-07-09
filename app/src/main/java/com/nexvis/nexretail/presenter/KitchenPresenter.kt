package com.nexvis.nexretail.presenter

import com.danny.coremodule.CoreImpl
import com.nexvis.nexretail.interactor.KitchenInteractor
import org.json.JSONObject

class KitchenPresenter :CoreImpl<KitchenInteractor.View>(), KitchenInteractor.Presenter {
    override fun onKitchen(body: JSONObject) {
        TODO("Not yet implemented")
    }
}