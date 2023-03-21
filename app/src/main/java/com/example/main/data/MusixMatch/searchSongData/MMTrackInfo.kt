package com.example.main.data.MusixMatch.searchSongData

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MMTrackInfo(
    @Json(name = "track_id") val songID: Int,
    @Json(name = "track_name") val songName: String,
    val has_lyrics: Int,
    val artist_name: String
)
