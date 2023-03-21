package com.example.main.data.MusixMatch.searchLyricData

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MMLyricBody(
    @Json(name = "body") val mmLyricBody: MMLyrics
)
