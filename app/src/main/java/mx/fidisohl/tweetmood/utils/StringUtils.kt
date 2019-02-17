package mx.fidisohl.tweetmood.utils

object StringUtils {

    @JvmStatic
    fun isNotNullOrEmpty(string: String?): Boolean {
        return !string.isNullOrEmpty()
    }

}