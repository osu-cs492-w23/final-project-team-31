package com.example.main.api

import com.example.main.data.MusixMatch.searchLyricData.MMLyricResult
import com.example.main.data.MusixMatch.searchSongData.MMSongResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MusixMatchService {
    @GET("track.lyrics.get")
    suspend fun getSongLyrics(
        @Query("track_id") trackID: Int,
        @Query("apikey") apiKey: String = "13f51e4b4894a8bf67e26e4bf2ade835",
    ): Response<MMLyricResult>

    @GET("track.search")
    suspend fun getTrackID(
        @Query("q_track") songName: String,
        @Query("q_artist") artistName: String,
        @Query("page_size") pageSize: String = "1",
        @Query("s_track_rating") songRating: String = "desc",
        @Query("apikey") apiKey: String = "13f51e4b4894a8bf67e26e4bf2ade835"
    ): Response<MMSongResult>

    companion object {
        private const val BASE_URL = "https://api.musixmatch.com/ws/1.1/"
        fun create() : MusixMatchService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory((MoshiConverterFactory.create()))
                .build()
                .create(MusixMatchService::class.java)
        }
    }
}
