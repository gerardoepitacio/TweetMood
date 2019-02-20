package mx.fidisohl.tweetmood.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import mx.fidisohl.tweetmood.R
import mx.fidisohl.tweetmood.models.ENetworkStatus
import mx.fidisohl.tweetmood.models.google.AnalysisResponse

object ViewUtils {

    @JvmStatic
    fun networkCallProcessing(nStatus: ENetworkStatus): Boolean {
        return nStatus == ENetworkStatus.PROCESS
    }

    fun hideSoftwKeyboard(activity: AppCompatActivity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

    @JvmStatic
    fun getEmojiFromResult(analysisResponse: AnalysisResponse?): String {
        if (analysisResponse != null) {
            return when (getMood(analysisResponse)) {
                EMood.MOOD_HAPPY -> getEmojiByUnicode(Constants.Emojis.EMOJI_HAPPY)
                EMood.MOOD_NEUTRAL -> getEmojiByUnicode(Constants.Emojis.EMOJI_NEUTRAL)
                EMood.MOOD_SAD -> getEmojiByUnicode(Constants.Emojis.EMOJI_SAD)
            }
        } else return ""
    }

    @JvmStatic
    fun getTextFromResult(analysisResponse: AnalysisResponse?): String {
        if (analysisResponse != null) {
            return when (getMood(analysisResponse)) {
                EMood.MOOD_HAPPY -> TweetMoodApp.appContext.getString(R.string.happy_tweet)
                EMood.MOOD_NEUTRAL -> TweetMoodApp.appContext.getString(R.string.neutral_tweet)
                EMood.MOOD_SAD -> TweetMoodApp.appContext.getString(R.string.sad_tweet)
            }
        } else return ""
    }

    @JvmStatic
    fun getColorFromResult(analysisResponse: AnalysisResponse?): Int {
        return if (analysisResponse != null) {
            when (getMood(analysisResponse)) {
                EMood.MOOD_HAPPY -> ContextCompat.getColor(TweetMoodApp.appContext, R.color.colorHappy)
                EMood.MOOD_NEUTRAL -> ContextCompat.getColor(TweetMoodApp.appContext, R.color.colorNeutral)
                EMood.MOOD_SAD -> ContextCompat.getColor(TweetMoodApp.appContext, R.color.colorSad)
            }
        } else ContextCompat.getColor(TweetMoodApp.appContext, R.color.colorTransparent)
    }

    private fun getMood(analysisResponse: AnalysisResponse): EMood {
        val score = analysisResponse.documentSentiment.score
        // TODO check if this is the best way to infer the results.
        return if (score > 0.25) {
            EMood.MOOD_HAPPY
        } else if (score > -0.75) {
            EMood.MOOD_NEUTRAL
        } else {
            EMood.MOOD_SAD
        }
    }
}

enum class EMood {
    MOOD_HAPPY, MOOD_NEUTRAL, MOOD_SAD
}