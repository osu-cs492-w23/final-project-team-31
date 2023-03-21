package com.example.main.data

import android.util.Log
import com.example.main.api.MusixMatchService
import com.example.main.data.MusixMatch.searchLyricData.MMLyricResult
import com.example.main.data.MusixMatch.searchSongData.MMSongResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusixMatchRepository(
    private val service: MusixMatchService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun doSongSearch(songName: String, artistName: String) : Result<MMSongResult?> =
        withContext(dispatcher){
            try {
                val response = service.getTrackID(songName, artistName)
                if (response.isSuccessful) {
                    Result.success(response.body())
                } else {
                    Result.failure(java.lang.Exception(response.errorBody()?.string()))
                }
            } catch (e: java.lang.Exception) {
                Result.failure(e)
            }
    }
    suspend fun doSongLyricSearch(trackID: Int)  : Result<MMLyricResult?> =
        withContext(dispatcher){
            try {
                val response = service.getSongLyrics(trackID)
                if (response.isSuccessful) {
                    Result.success(response.body())
                } else {
                    Result.failure(java.lang.Exception(response.errorBody()?.string()))
                }
            } catch (e: java.lang.Exception) {
                Result.failure(e)
            }
        }
}