package com.example.main.data.MusixMatch.searchLyricData

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MMLyrics(
    val lyrics: MMLyricInfo
)
