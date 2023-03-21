package com.example.main.data.MusixMatch.searchLyricData

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MMLyricResult(
    val message: MMLyricBody
)
