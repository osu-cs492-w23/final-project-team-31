package com.example.main.data.MusixMatch.searchSongData

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MMSongTrackList(
    @Json(name = "track_list") val trackList: List<MMSongTrack>
)
