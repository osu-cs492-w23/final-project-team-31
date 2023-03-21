package com.example.main.data.MusixMatch.searchSongData

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MMSongResult(
    val message: MMSongBody

)
