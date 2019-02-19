package mx.fidisohl.tweetmood.api

import mx.fidisohl.tweetmood.models.ENetworkStatus
import mx.fidisohl.tweetmood.models.NetworkRequest
import retrofit2.Call

abstract class CallbackNetworkStatusHandler<T>(private val handler: NetworkRequest) : GeneralCallback<T>() {

    init {
        if (handler.eRequestStatus != ENetworkStatus.PROCESS) {
            handler.eRequestStatus = ENetworkStatus.PROCESS
        }
        if (handler.errorMessage.isNotEmpty()) {
            handler.errorMessage = ""
        }
        if (handler.responseCode != null) {
            handler.responseCode = null
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        super.onFailure(call, t)
        handler.eRequestStatus = ENetworkStatus.FAILED

    }

    override fun controlNoSuccessful(responseCode: Int) {
        handler.responseCode = responseCode
        handler.eRequestStatus = ENetworkStatus.FAILED
    }
}