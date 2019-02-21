package mx.fidisohl.tweetmood.ui.analysis


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.fidisohl.tweetmood.api.CallbackNetworkStatusHandler
import mx.fidisohl.tweetmood.api.ResourceGenerator
import mx.fidisohl.tweetmood.models.ENetworkStatus
import mx.fidisohl.tweetmood.models.NetworkRequest
import mx.fidisohl.tweetmood.models.TweetModel
import mx.fidisohl.tweetmood.models.google.AnalysisResponse
import mx.fidisohl.tweetmood.models.google.Document
import mx.fidisohl.tweetmood.models.google.RequestSentimentAnalysisData
import retrofit2.Call


class TweetAnalysisViewModel : ViewModel() {

    val networkCalls = NetworkRequest()
    val analysisResponse = MutableLiveData<AnalysisResponse>()

    fun analyzeTweet(tweet: TweetModel){
        // TODO fetch the whole tweet content if it was truncated
        ResourceGenerator.GOOGLE_RESOURCES.analyseSentiment(RequestSentimentAnalysisData(Document(tweet.text)))
            .enqueue(object: CallbackNetworkStatusHandler<AnalysisResponse?>(networkCalls){
                override fun success(data: AnalysisResponse?) {
                    networkCalls.eRequestStatus = ENetworkStatus.SUCCESS
                    analysisResponse.postValue(data)
                }

                override fun controlNoSuccessful(responseCode: Int) {
                    networkCalls.responseCode = responseCode
                    networkCalls.eRequestStatus = ENetworkStatus.FAILED
                }

                override fun onFailure(call: Call<AnalysisResponse?>, t: Throwable) {
                    super.onFailure(call, t)
                    networkCalls.eRequestStatus = ENetworkStatus.FAILED
                }
            })
    }
}