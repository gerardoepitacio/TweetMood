package mx.fidisohl.tweetmood.models.google

import mx.fidisohl.tweetmood.utils.Constants

data class RequestSentimentAnalysisData(
    val document: Document,
    val encodingType: String = Constants.Google.DEFAULT_ENCODING
)