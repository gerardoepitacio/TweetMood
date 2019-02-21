package mx.fidisohl.tweetmood.utils

object Constants {

    object TwitterConstants {
        const val TWITTER_URL_BASE = "https://api.twitter.com/1.1/"
        const val TWITTER_ENDPOINT_USER_TIMELINE = "statuses/user_timeline.json"
        const val TWITTER_CONSUMER_KEY = "TWITTER_CONSUMER_KEY"
        const val TWITTER_CONSUMER_SECRET = "TWITTER_CONSUMER_SECRET"
        const val TWITTER_CUSTOMER_TOKEN = "TWITTER_CUSTOMER_TOKEN"
        const val TWITTER_CUSTOMER_TOKEN_SECRET = "TWITTER_CUSTOMER_TOKEN_SECRET"
    }

    object Google {
        const val GOOGLE_URL_BASE = "https://language.googleapis.com/"
        const val GOOGLE_API_KEY = "GOOGLE_API_KEY"
        const val GOOGLE_ENDPOINT_SENTIMENT_ANALYSIS = "v1/documents:analyzeSentiment?key=$GOOGLE_API_KEY"
        const val DEFAULT_ENCODING = "UTF8"
        const val DEFAULT_DOCUMENT_TYPE = "PLAIN_TEXT"
    }

    object Emojis {
        const val EMOJI_HAPPY = 0x1F603
        const val EMOJI_NEUTRAL = 0x1F610
        const val EMOJI_SAD = 0x1F614
        const val EMOJI_EMBARRASING = 0x1F648
    }

    object Transitions {
        const val SHARED_ELEMENT_NAME = "tweetContent"
    }

}