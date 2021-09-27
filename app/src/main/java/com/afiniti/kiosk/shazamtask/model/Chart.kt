package com.afiniti.kiosk.shazamtask.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Chart(
    var chart: Array<Track>
) : Parcelable


@Parcelize
data class Track(
    var type: String,
    var key: String,
    var heading: Heading,
    var images: Images,
    var stores: Stores,
    var streams: Streams,
    var share: Share,
    var alias: String,
    var url: String,
    var actions: List<Actions>
) : Parcelable

@Parcelize
data class Heading(
    var title: String,
    var subtitle: String
) : Parcelable

@Parcelize
data class Images(
    var default: String
) : Parcelable

@Parcelize
data class Actions(
    var type: String,
    var uri: String,
    var name: String
) : Parcelable

@Parcelize
data class Apple(
    var actions: List<Actions>,
    var explicit: Boolean,
    var previewurl: String,
    var coverarturl: String,
    var trackid: String,
    var productid: String
) : Parcelable

@Parcelize
data class Stores(
    var apple: Apple
) : Parcelable

@Parcelize
data class Deezer(
    var actions: List<Actions>
) : Parcelable

@Parcelize
data class Spotify(
    var actions: List<Actions>
) : Parcelable

@Parcelize
data class Streams(
    var deezer: Deezer,
    var spotify: Spotify
) : Parcelable


@Parcelize
data class Share(
    var subject: String,
    var text: String,
    var href: String,
    var image: String,
    var twitter: String,
    var html: String,
    var avatar: String,
    var snapchat: String
) : Parcelable
