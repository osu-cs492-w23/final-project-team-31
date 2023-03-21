package com.example.main.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main.api.MusixMatchService
import com.example.main.data.MusixMatch.searchLyricData.MMLyricResult
import com.example.main.data.MusixMatch.searchSongData.MMSongResult
import com.example.main.data.MusixMatchRepository
import kotlinx.coroutines.launch

class MusixMatchViewModel : ViewModel() {
    private val repository = MusixMatchRepository(MusixMatchService.create())

    private val _songData = MutableLiveData<MMSongResult?>(null)
    val songData : LiveData<MMSongResult?> = _songData

    private val _lyricData = MutableLiveData<MMLyricResult?>(null)
    val lyricData : LiveData<MMLyricResult?> = _lyricData

    fun getSongData(songName: String, artistName: String) {
        viewModelScope.launch {
            val result = repository.doSongSearch(songName, artistName)
            if (result.isSuccess) {
                _songData.value = result.getOrNull()
            }
            else if (result.isFailure) {
                Log.d("MusixMatchViewModel", "getSongData query failed with error ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun getLyricData(trackID: Int) {
        viewModelScope.launch {
            val result = repository.doSongLyricSearch(trackID)
            if(result.isSuccess) {
            _lyricData.value = result.getOrNull()
            }
            else if (result.isFailure) {
                Log.d("MusixMatchViewModel", "getLyricData query failed with error ${result.exceptionOrNull()?.message}")
            }
        }
    }
}