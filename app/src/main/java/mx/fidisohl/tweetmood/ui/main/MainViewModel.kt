package mx.fidisohl.tweetmood.ui.main


import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.fidisohl.tweetmood.api.CallbackNetworkStatusHandler
import mx.fidisohl.tweetmood.api.ResourceGenerator
import mx.fidisohl.tweetmood.models.ENetworkStatus
import mx.fidisohl.tweetmood.models.NetworkRequest
import mx.fidisohl.tweetmood.models.TweetModel



class MainViewModel : ViewModel() {

    val username = ObservableField<String>("")
    val networkCalls = NetworkRequest()
    val tweetList = MutableLiveData<List<TweetModel>>()

    fun btnMainClick(view: View){
        ResourceGenerator.TWITTER_RESOURCES.getUserTweets(username.get()!!).enqueue(
            object: CallbackNetworkStatusHandler<List<TweetModel>>(networkCalls){
                override fun success(data: List<TweetModel>) {
                    networkCalls.eRequestStatus = ENetworkStatus.SUCCESS
                    if (!data.isNullOrEmpty()) {
                        tweetList.postValue(data)
                    }
                }
            }
        )
    }

    fun tweetsListEmpty(): Boolean {
        return tweetList.value.isNullOrEmpty()
    }
}