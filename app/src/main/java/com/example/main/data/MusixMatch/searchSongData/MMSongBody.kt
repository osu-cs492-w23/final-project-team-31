package com.example.main.data.MusixMatch.searchSongData

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MMSongBody(
    @Json(name = "body") val mmSongBody: MMSongTrackList
)