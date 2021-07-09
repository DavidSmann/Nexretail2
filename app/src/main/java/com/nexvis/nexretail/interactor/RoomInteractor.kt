package com.nexvis.nexretail.interactor

import com.danny.coremodule.CorePresenter
import com.danny.coremodule.CoreView
import org.json.JSONObject

interface RoomInteractor {
    interface View : CoreView {
        fun onRoomResponse(data: JSONObject)
        fun onRoomFailed(message: String)
    }

    interface Presenter : CorePresenter<View> {
        fun onRoom(body: JSONObject)
    }
}