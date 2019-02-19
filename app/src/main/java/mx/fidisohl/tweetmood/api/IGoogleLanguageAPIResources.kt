package mx.fidisohl.tweetmood.api

import mx.fidisohl.tweetmood.models.google.AnalysisResponse
import mx.fidisohl.tweetmood.models.google.RequestSentimentAnalysisData
import mx.fidisohl.tweetmood.utils.Constants.Google.GOOGLE_ENDPOINT_SENTIMENT_ANALYSIS
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IGoogleLanguageAPIResources {

    @POST(GOOGLE_ENDPOINT_SENTIMENT_ANALYSIS)
    fun analyseSentiment(@Body requestValue: RequestSentimentAnalysisData): Call<AnalysisResponse?>
}