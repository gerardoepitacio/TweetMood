package mx.fidisohl.tweetmood.models.google

import mx.fidisohl.tweetmood.utils.Constants.Google.DEFAULT_DOCUMENT_TYPE

data class Document(
    val content: String,
    val type: String = DEFAULT_DOCUMENT_TYPE
)