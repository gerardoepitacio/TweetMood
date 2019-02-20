package mx.fidisohl.tweetmood.utils

import android.app.Application
import android.content.Context

/**
 * Application class.
 *
 * @author Gerardo Epitacio - juan.epitacio@qacg.com
 * @version 1.0
 */
class TweetMoodApp : Application() {

    init {
        appContext = this
    }

    companion object {
        lateinit var appContext: Context
    }

}