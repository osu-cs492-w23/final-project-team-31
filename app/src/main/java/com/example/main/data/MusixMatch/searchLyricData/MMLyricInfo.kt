package com.example.main.data.MusixMatch.searchLyricData

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MMLyricInfo(
    @Json(name = "lyrics_body") val lyrics: String,
)
