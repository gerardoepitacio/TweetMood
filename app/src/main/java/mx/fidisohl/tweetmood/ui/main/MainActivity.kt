package mx.fidisohl.tweetmood.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_loader.*
import mx.fidisohl.tweetmood.BR
import mx.fidisohl.tweetmood.R
import mx.fidisohl.tweetmood.databinding.ActivityMainBinding
import mx.fidisohl.tweetmood.models.ENetworkStatus
import mx.fidisohl.tweetmood.models.NetworkRequest
import mx.fidisohl.tweetmood.models.TweetModel
import mx.fidisohl.tweetmood.ui.adapters.TweetsAdapter
import mx.fidisohl.tweetmood.ui.analysis.AnalysisActivity
import mx.fidisohl.tweetmood.utils.Constants.Transitions.SHARED_ELEMENT_NAME
import mx.fidisohl.tweetmood.utils.ViewUtils


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvTweets: RecyclerView
    private val adapter: TweetsAdapter by lazy {
        TweetsAdapter{ tweetSelected: TweetModel, view: View->
            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, view, SHARED_ELEMENT_NAME)
            startActivity(AnalysisActivity.getActivityIntent(this@MainActivity, tweetSelected), options.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init() {
        rvTweets = binding.rvUserTweets
        rvTweets.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@MainActivity)
        rvTweets.setHasFixedSize(true)
        rvTweets.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.vm = viewModel

        viewModel.tweetList.observe(this, Observer {
            setTweetsOwner()
            binding.vm = viewModel
            if (!it.isNullOrEmpty()) {
                adapter.showTweets(it)
            }
        })

        viewModel.networkCalls.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (propertyId == BR.eRequestStatus) {

                    val networkCall = sender as NetworkRequest
                    when (networkCall.eRequestStatus) {
                        ENetworkStatus.NONE -> {}
                        ENetworkStatus.PROCESS -> {
                            loaderText.text = resources.getString(R.string.request_username_placeholder, viewModel.username.get())
                            ViewUtils.hideSoftwKeyboard(this@MainActivity)
                        }
                        ENetworkStatus.SUCCESS -> {
                            setTweetsOwner()
                        }
                        ENetworkStatus.FAILED -> {
                            manageErrors(networkCall)
                        }
                    }
                }
            }
        })

        setupInitialState()
    }

    private fun setupInitialState() {
        if (viewModel.networkCalls.eRequestStatus == ENetworkStatus.FAILED) {
            manageErrors(viewModel.networkCalls)
        }
    }

    private fun setTweetsOwner() {
        tvUserNameTweets.text = resources.getString(R.string.username_tweets_placeholder, viewModel.username.get())
        tvUserNameTweets.visibility = View.VISIBLE
    }

    private fun showErrorMessage(message: String) {
        tvNotData.text = message
    }

    private fun showServiceNotAvalableMsg() {
        showErrorMessage(resources.getString(R.string.service_not_available))
    }

    private fun showServerNotReachedMsg() {
        showErrorMessage(resources.getString(R.string.verify_internet_conection))
    }

    fun manageErrors(networkCall: NetworkRequest) {
        viewModel.tweetList.value = null
        tvUserNameTweets.visibility = View.GONE
        if (networkCall.responseCode != null) {
            when (networkCall.responseCode) {
                401 -> {
                    tvNotData.text = resources.getString(R.string.issues_feching_tweets_placeholder, viewModel.username.get())
                }
                404 -> {
                    tvNotData.text = resources.getString(R.string.user_not_found_placeholder, viewModel.username.get())
                }
                in 400..599 -> showServiceNotAvalableMsg()
            }
        } else {
            showServerNotReachedMsg()
        }
    }

}
