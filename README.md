# TweetMood
Analyzes the latest Tweets of a user and tells you if the Tweet contains a Sad, Neutral or Happy sentiment, all this with gorgeous emojis.

# Main feactures
* Made with [Kotlin](https://kotlinlang.org/)
* MVVM architecture pattern as the latest [Google architecture guidelines to create Android Apps](https://developer.android.com/jetpack/docs/guide).
* Supports mainly versions 19 to 28.
* Portrait orientation only.

# Third party APIS
* [Oficial Twitter API](https://developer.twitter.com/) - To list user tweets.
* [Cloud Natural Language - Sentimental Analysis API](https://cloud.google.com/natural-language/) - Words analysis.

# Main Libraries
## Jetpack components
* [Android X](https://developer.android.com/jetpack/androidx/) - It have the major improvements to the original Android Support Library.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel/) - Class designed to store and manage UI-related data in a lifecycle conscious way.
* [Databinding](https://developer.android.com/topic/libraries/data-binding/) - Allows you to bind UI components in your layouts to data sources in your app using a declarative format.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata/) - An observable data holder class with is lifecycle aware.

## Third party libraries
* [Retrofit](https://square.github.io/retrofit/) - To consume Twitter and Google APIs.
* [Glide](https://github.com/bumptech/glide) - An open source image loading framework for Android.
* [Android material components](https://material.io/develop/android/docs/getting-started/) - Android material components.
* [OAuth 1.0 support](https://github.com/pakerfeldt/okhttp-signpost) - A Signpost extension for signing OkHttp requests.

# Authors
* [Gerardo Epitacio](https://twitter.com/gerardoepitacio)

# Google Play
* Find it at [Google Play](https://play.google.com/store/apps/details?id=mx.fidisohl.tweetmood)

# Suggested improvements
1. // TODO Fetch the whole tweet content if it was truncated, before make the words analysis.
2. // TODO Define or search for a better framework/model to identify the Happy, Neutral and Sad sentiment.
3. // TODO Sopport for a landscape orientation.
4. // TODO Improve UI/UX.

