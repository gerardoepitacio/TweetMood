package mx.fidisohl.tweetmood.utils

import android.app.Application
import android.content.Context
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat

/**
 * Application class.
 *
 * @author Gerardo Epitacio - juan.epitacio@qacg.com
 * @version 1.0
 */
class TweetMoodApp : Application() {

    val TAG = TweetMoodApp::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
        initEmojiCompat()
    }

    private fun initEmojiCompat() {
        val config: EmojiCompat.Config
        // Use the bundled font for EmojiCompat
        config = BundledEmojiCompatConfig(applicationContext)
        config.setReplaceAll(true)
            .registerInitCallback(object : EmojiCompat.InitCallback() {
                override fun onInitialized() {
                    // Implementation not required.
                }

                override fun onFailed(throwable: Throwable?) {
                    super.onFailed(throwable)
                    // Implementation not required.
                }
            })

        EmojiCompat.init(config)
    }

    init {
        appContext = this
    }

    companion object {
        lateinit var appContext: Context
    }

}