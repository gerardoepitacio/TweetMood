package mx.fidisohl.tweetmood.ui.analysis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_analysis.*
import mx.fidisohl.tweetmood.BR
import mx.fidisohl.tweetmood.R
import mx.fidisohl.tweetmood.databinding.ActivityAnalysisBinding
import mx.fidisohl.tweetmood.models.ENetworkStatus
import mx.fidisohl.tweetmood.models.NetworkRequest
import mx.fidisohl.tweetmood.models.TweetModel
import mx.fidisohl.tweetmood.utils.Constants
import mx.fidisohl.tweetmood.utils.TweetMoodApp
import mx.fidisohl.tweetmood.utils.ViewUtils

class AnalysisActivity : AppCompatActivity() {

    private lateinit var viewModel: TweetAnalysisViewModel
    private lateinit var binding: ActivityAnalysisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_analysis)
        val tweet = intent.getParcelableExtra(KEY_TWEET) as TweetModel?
        if (tweet != null) {
            init(tweet)
        }
    }

    private fun init(tweet: TweetModel) {
        viewModel = ViewModelProviders.of(this).get(TweetAnalysisViewModel::class.java)
        binding.vm = viewModel
        binding.tweet = tweet
        viewModel.analysisResponse.observe(this, Observer {
            if (it != null) {
                binding.result = it
            } else {
                showGeneralError()
            }
        })

        viewModel.networkCalls.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (propertyId == BR.eRequestStatus) {
                    val networkCall = sender as NetworkRequest
                    binding.vm = viewModel
                    when (networkCall.eRequestStatus) {
                        ENetworkStatus.NONE -> {}
                        ENetworkStatus.PROCESS -> {
                            loaderText.text = resources.getString(R.string.analyzing_twet)
                        }
                        ENetworkStatus.SUCCESS -> {}
                        ENetworkStatus.FAILED -> {
                            showGeneralError()
                        }
                    }
                }
            }
        })

        Glide.with(TweetMoodApp.appContext)
            .load(tweet.user?.profile_image_url_https)
            .apply(RequestOptions.circleCropTransform())
            .into(imgProfile)

        btnAccept.setOnClickListener {
            onBackPressed()
        }

        viewModel.analyzeTweet(tweet)
    }

    companion object {
        private const val KEY_TWEET = "tweet"
        fun getActivityIntent(context: Context, tweet: TweetModel): Intent {
            val intent = Intent(context, AnalysisActivity::class.java)
            intent.putExtra(KEY_TWEET, tweet)
            return intent
        }
    }

    private fun showGeneralError() {
        tvEmoji.text = ViewUtils.getEmojiByUnicode(Constants.Emojis.EMOJI_EMBARRASING)
        tvResult.text = resources.getText(R.string.couldnt_analyze_tweet)
    }
}
