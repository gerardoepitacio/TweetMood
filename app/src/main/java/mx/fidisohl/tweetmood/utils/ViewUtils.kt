package mx.fidisohl.tweetmood.utils

import mx.fidisohl.tweetmood.models.ENetworkStatus

object ViewUtils {

    @JvmStatic
    fun networkCallProcessing(nStatus: ENetworkStatus): Boolean {
        return nStatus == ENetworkStatus.PROCESS
    }


}