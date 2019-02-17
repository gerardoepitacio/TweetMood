package mx.fidisohl.tweetmood.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_tweet.view.*
import mx.fidisohl.tweetmood.R
import mx.fidisohl.tweetmood.models.Tweet

class TweetsAdapter(val context: Context, private val callback: (Tweet, Int) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<TweetsAdapter.TweetViewHolder>() {

    private var tweets: List<Tweet>? = null

    fun showTweets(data: List<Tweet>) {
        this.tweets = data
        notifyDataSetChanged()
    }

    private lateinit var viewHolder: TweetViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        viewHolder = TweetViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tweet, parent, false))
        return viewHolder
    }

    override fun getItemCount(): Int {
        return if(tweets?.size != null ) tweets!!.size else 0
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) =
        holder.bindEvaluation(tweets!![position], callback)
    class TweetViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bindEvaluation(
            tweet: Tweet,
            callback: (Tweet, Int) -> Unit
        ) {
            itemView.tvTweetContent.setText(tweet.content.toString())
            itemView.setOnClickListener {
                callback(tweet, this.adapterPosition)
            }
        }
    }

}
