package mx.fidisohl.tweetmood.models

import android.os.Parcel
import android.os.Parcelable

data class TweetModel (
    val created_at: String,
    val id: Long,
    val id_str: String,
    val lang: String,
    val possibly_sensitive: Boolean,
    val retweeted: Boolean,
    val source: String,
    val text: String,
    val truncated: Boolean,
    val user: User?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(User::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(created_at)
        parcel.writeLong(id)
        parcel.writeString(id_str)
        parcel.writeString(lang)
        parcel.writeByte(if (possibly_sensitive) 1 else 0)
        parcel.writeByte(if (retweeted) 1 else 0)
        parcel.writeString(source)
        parcel.writeString(text)
        parcel.writeByte(if (truncated) 1 else 0)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TweetModel> {
        override fun createFromParcel(parcel: Parcel): TweetModel {
            return TweetModel(parcel)
        }

        override fun newArray(size: Int): Array<TweetModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class User(
    val created_at: String,
    val default_profile: Boolean,
    val default_profile_image: Boolean,
    val description: String,
    val id: Long,
    val id_str: String,
    val name: String,
    val profile_image_url: String,
    val profile_image_url_https: String,
    val screen_name: String,
    val verified: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(created_at)
        parcel.writeByte(if (default_profile) 1 else 0)
        parcel.writeByte(if (default_profile_image) 1 else 0)
        parcel.writeString(description)
        parcel.writeLong(id)
        parcel.writeString(id_str)
        parcel.writeString(name)
        parcel.writeString(profile_image_url)
        parcel.writeString(profile_image_url_https)
        parcel.writeString(screen_name)
        parcel.writeByte(if (verified) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}