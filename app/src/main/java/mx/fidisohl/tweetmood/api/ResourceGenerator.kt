package mx.fidisohl.tweetmood.api

import mx.fidisohl.tweetmood.utils.Constants
import mx.fidisohl.tweetmood.utils.Constants.TwitterConstants.TWITTER_CONSUMER_KEY
import mx.fidisohl.tweetmood.utils.Constants.TwitterConstants.TWITTER_CONSUMER_SECRET
import mx.fidisohl.tweetmood.utils.Constants.TwitterConstants.TWITTER_CUSTOMER_TOKEN
import mx.fidisohl.tweetmood.utils.Constants.TwitterConstants.TWITTER_CUSTOMER_TOKEN_SECRET
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import java.lang.reflect.Type

class ResourceGenerator private constructor() {

    companion object {

        private val loggingInterceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private val consumerObject = OkHttpOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET).apply {
            this.setTokenWithSecret(TWITTER_CUSTOMER_TOKEN, TWITTER_CUSTOMER_TOKEN_SECRET)
        }

        private val twitterClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(SigningInterceptor(consumerObject))
                .addInterceptor(loggingInterceptor)
                .build()

        private val googleClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        private val retrofitTwitterClient: Retrofit = Retrofit.Builder()
                .addConverterFactory(nullOnEmptyConverterFactory)
                .addConverterFactory(GsonConverterFactory.create())
                .client(twitterClient)
                .baseUrl(Constants.TwitterConstants.TWITTER_URL_BASE)
                .build()

        private val retrofitGoogleClient: Retrofit = Retrofit.Builder()
                .addConverterFactory(nullOnEmptyConverterFactory)
                .addConverterFactory(GsonConverterFactory.create())
                .client(googleClient)
                .baseUrl(Constants.Google.GOOGLE_URL_BASE)
                .build()

        val TWITTER_RESOURCES: ITwitterResources = retrofitTwitterClient.create(ITwitterResources::class.java)
        val GOOGLE_RESOURCES: IGoogleLanguageAPIResources = retrofitGoogleClient.create(IGoogleLanguageAPIResources::class.java)
    }
}

val nullOnEmptyConverterFactory = object : Converter.Factory() {
    fun converterFactory() = this
    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
        val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
        override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
    }
}