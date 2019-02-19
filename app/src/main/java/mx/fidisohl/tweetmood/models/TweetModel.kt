package mx.fidisohl.tweetmood.models

data class TweetModel(
    val created_at: String,
    val id: Long,
    val id_str: String,
    val lang: String,
    val possibly_sensitive: Boolean,
    val retweeted: Boolean,
    val source: String,
    val text: String,
    val truncated: Boolean,
    val user: User
)

data class User(
    val created_at: String,
    val default_profile: Boolean,
    val default_profile_image: Boolean,
    val description: String,
    val id: Int,
    val id_str: String,
    val name: String,
    val profile_image_url: String,
    val profile_image_url_https: String,
    val screen_name: String,
    val verified: Boolean
)