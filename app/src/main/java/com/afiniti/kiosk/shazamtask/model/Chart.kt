package com.afiniti.kiosk.shazamtask.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Chart(
    @SerializedName("chart") var chart: Array<Track>
) : Parcelable


@Parcelize
data class Track(
    @SerializedName("type") var type: String,
    @SerializedName("key") var key: String,
    @SerializedName("heading") var heading: Heading,
    @SerializedName("images") var images: Images,
    @SerializedName("stores") var stores: Stores,
    @SerializedName("streams") var streams: Streams,
    @SerializedName("share") var share: Share,
    @SerializedName("alias") var alias: String,
    @SerializedName("url") var url: String,
    @SerializedName("actions") var actions: List<Actions>
) : Parcelable

@Parcelize
data class Heading(
    @SerializedName("title") var title: String,
    @SerializedName("subtitle")  var subtitle: String
) : Parcelable

@Parcelize
data class Images(
    @SerializedName("default") var default: String
) : Parcelable

@Parcelize
data class Actions(
    @SerializedName("type") var type: String,
    @SerializedName("uri") var uri: String,
    @SerializedName("name") var name: String
) : Parcelable

@Parcelize
data class Apple(
    @SerializedName("actions") var actions: List<Actions>,
    @SerializedName("explicit") var explicit: Boolean,
    @SerializedName("previewurl") var previewUrl: String,
    @SerializedName("coverarturl") var coverartUrl: String,
    @SerializedName("trackid") var trackId: String,
    @SerializedName("productid")  var productId: String
) : Parcelable

@Parcelize
data class Stores(
    @SerializedName("apple") var apple: Apple
) : Parcelable

@Parcelize
data class Deezer(
    @SerializedName("actions") var actions: List<Actions>
) : Parcelable

@Parcelize
data class Spotify(
    @SerializedName("actions") var actions: List<Actions>
) : Parcelable

@Parcelize
data class Streams(
    @SerializedName("deezer") var deezer: Deezer,
    @SerializedName("spotify") var spotify: Spotify
) : Parcelable


@Parcelize
data class Share(
    @SerializedName("subject") var subject: String,
    @SerializedName("text")  var text: String,
    @SerializedName("href")  var href: String,
    @SerializedName("image")  var image: String,
    @SerializedName("twitter") var twitter: String,
    @SerializedName("html")  var html: String,
    @SerializedName("avatar") var avatar: String,
    @SerializedName("snapchat")  var snapchat: String
) : Parcelable
