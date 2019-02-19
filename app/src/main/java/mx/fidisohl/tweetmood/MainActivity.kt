package mx.fidisohl.tweetmood

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_loader.*
import mx.fidisohl.tweetmood.api.GeneralCallback
import mx.fidisohl.tweetmood.api.ResourceGenerator.Companion.GOOGLE_RESOURCES
import mx.fidisohl.tweetmood.databinding.ActivityMainBinding
import mx.fidisohl.tweetmood.models.ENetworkStatus
import mx.fidisohl.tweetmood.models.NetworkRequest
import mx.fidisohl.tweetmood.models.TweetModel
import mx.fidisohl.tweetmood.models.google.AnalysisResponse
import mx.fidisohl.tweetmood.models.google.Document
import mx.fidisohl.tweetmood.models.google.RequestSentimentAnalysisData
import mx.fidisohl.tweetmood.ui.adapters.TweetsAdapter
import mx.fidisohl.tweetmood.ui.main.MainViewModel
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvTweets: RecyclerView
    private val adapter: TweetsAdapter by lazy {
        TweetsAdapter(this@MainActivity) { tweetSelected: TweetModel, position: Int ->
            Log.d("", tweetSelected.toString())
            // TODO Start tweet content analysis
            GOOGLE_RESOURCES.analyseSentiment(RequestSentimentAnalysisData(Document(tweetSelected.text)))
                .enqueue(object: GeneralCallback<AnalysisResponse?>(){
                    override fun success(data: AnalysisResponse?) {
                        Log.d("--->", data?.toString())
                        if (data != null) {
                            val score = data.documentSentiment.score
                            if (score > 0.25) {
                                Toast.makeText(this@MainActivity, "POSITIVE", Toast.LENGTH_LONG).show()
                            } else if (score > -0.75) {
                                Toast.makeText(this@MainActivity, "NEUTRAL", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@MainActivity, "NEGATIVE", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    override fun controlNoSuccessful(responseCode: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onFailure(call: Call<AnalysisResponse?>, t: Throwable) {
                        super.onFailure(call, t)
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    fun showErrorMessage(message: String) {
        tvNotData.text = message
    }

    fun showServiceNotAvalableMsg() {
        showErrorMessage(resources.getString(R.string.service_not_available))
    }

    fun showServerNotReachedMsg() {
        showErrorMessage(resources.getString(R.string.verify_internet_conection))
    }

    fun manageErrors(networkCall: NetworkRequest) {
        viewModel.tweetList.value = null
        tvUserNameTweets.visibility = View.GONE
        if (networkCall.responseCode != null) {
            when (networkCall.responseCode) {
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
