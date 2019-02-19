package mx.fidisohl.tweetmood.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class NetworkRequest : BaseObservable() {


    var responseCode: Int? = null

    var errorMessage: String = ""

    @get:Bindable
    var eRequestStatus: ENetworkStatus = ENetworkStatus.NONE
        set(value) {
            field = value
            notifyPropertyChanged(BR.eRequestStatus)
        }

}

enum class ENetworkStatus {
    NONE,
    PROCESS,
    SUCCESS,
    FAILED
}