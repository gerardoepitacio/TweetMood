package mx.fidisohl.tweetmood.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_tweet.view.*
import mx.fidisohl.tweetmood.R
import mx.fidisohl.tweetmood.models.TweetModel
import mx.fidisohl.tweetmood.utils.TweetMoodApp


class TweetsAdapter(private val callback: (TweetModel, View) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<TweetsAdapter.TweetViewHolder>() {

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
        holder.bindEvaluation(tweets!![position], callback)
    class TweetViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bindEvaluation(
            tweet: TweetModel,
            callback: (TweetModel, View) -> Unit
        ) {
            itemView.setOnClickListener {
                callback(tweet, itemView.tvTweetContent)
            }
            itemView.tvTweetContent.text = tweet.text

            Glide.with(TweetMoodApp.appContext)
                .load(tweet.user?.profile_image_url_https)
                .placeholder(R.drawable.ic_person)
                .apply(RequestOptions.circleCropTransform())
                .into(itemView.imgProfile)

        }
    }

}
