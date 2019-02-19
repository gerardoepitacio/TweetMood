package mx.fidisohl.tweetmood.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_tweet.view.*
import mx.fidisohl.tweetmood.models.TweetModel


class TweetsAdapter(val context: Context, private val callback: (TweetModel, Int) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<TweetsAdapter.TweetViewHolder>() {

    private var tweets: List<TweetModel>? = null

    fun showTweets(data: List<TweetModel>) {
        this.tweets = data
        notifyDataSetChanged()
    }

    private lateinit var viewHolder: TweetViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        viewHolder = TweetViewHolder(LayoutInflater.from(parent.context).inflate(mx.fidisohl.tweetmood.R.layout.item_tweet, parent, false))
        return viewHolder
    }

    override fun getItemCount(): Int {
        return if(tweets?.size != null ) tweets!!.size else 0
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) =
        holder.bindEvaluation(tweets!![position], position, callback)
    class TweetViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bindEvaluation(
            tweet: TweetModel,
            position: Int,
            callback: (TweetModel, Int) -> Unit
        ) {
            itemView.setOnClickListener {
                callback(tweet, this.adapterPosition)
            }
            itemView.tvTweetContent.text = tweet.text
            itemView.tvTweetNumber.text = (position + 1).toString()

        }
    }

}
