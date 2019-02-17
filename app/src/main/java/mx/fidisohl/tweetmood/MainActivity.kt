package mx.fidisohl.tweetmood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import mx.fidisohl.tweetmood.databinding.ActivityMainBinding
import mx.fidisohl.tweetmood.models.Tweet
import mx.fidisohl.tweetmood.ui.adapters.TweetsAdapter
import mx.fidisohl.tweetmood.ui.main.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvTweets: RecyclerView
    private val adapter: TweetsAdapter by lazy {
        TweetsAdapter(this@MainActivity) { tweetSelected: Tweet, position: Int ->
            // TODO Start tweet content analysis
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.vm = viewModel


        rvTweets = binding.rvUserTweets
        rvTweets.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@MainActivity)
        rvTweets.setHasFixedSize(true)
        rvTweets.adapter = adapter

        adapter.showTweets(getDummyTweets())

    }

    private fun getDummyTweets(): List<Tweet> {
        val tweets = ArrayList<Tweet>()
        val dummyContent = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed consectetur diam metus, at aliquam velit."
        for (i in 1..100) tweets.add(Tweet("Dummy User$i", Calendar.getInstance().timeInMillis, dummyContent))
        return tweets
    }

}
