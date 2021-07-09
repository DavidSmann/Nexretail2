package com.nexvis.nexretail.presenter

import com.danny.coremodule.CoreImpl
import com.nexvis.nexretail.interactor.RoomInteractor
import org.json.JSONObject

class RoomPresenter : CoreImpl<RoomInteractor.View>(), RoomInteractor.Presenter{
    override fun onRoom(body: JSONObject) {
        TODO("Not yet implemented")
    }
}