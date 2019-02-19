package mx.fidisohl.tweetmood.api

import mx.fidisohl.tweetmood.models.TweetModel
import mx.fidisohl.tweetmood.utils.Constants.TwitterConstants.TWITTER_ENDPOINT_USER_TIMELINE
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITwitterResources {

    @GET(TWITTER_ENDPOINT_USER_TIMELINE)
    fun getUserTweets(
        @Query("screen_name") screenName: String,
        @Query("exclude_replies") excludeReplies: Boolean= true
    ): Call<List<TweetModel>>

}