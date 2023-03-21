package com.example.main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.main.api.MusixMatchService
import com.example.main.data.MusixMatch.searchLyricData.MMLyricResult
import com.example.main.data.MusixMatch.searchSongData.MMSongResult
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var appBarConfig: AppBarConfiguration
    private val mediaPlayer = MediaPlayer()
    private val musicMatchService = MusixMatchService.create()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(
                R.id.nav_host_fragment
            ) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfig = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(
            navController,
            appBarConfig
        )

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)


        // Listen for changes to the preference value
        sharedPref.registerOnSharedPreferenceChangeListener(this)

        // Start playing the media if the preference is enabled
        if (sharedPref.getBoolean(getString(R.string.pref_audio_key), true)) {
            playMedia()
        }
    }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, key: String?) {
        Log.d("MainActivity", "Preference setting clicked. Key: ${key}")
        if (key == getString(R.string.pref_audio_key)) {
            val enableAudio = prefs?.getBoolean(key, true)?: false
            if (enableAudio) {
                if (!mediaPlayer.isPlaying) {
                    // If the media player is not playing, start it
                    mediaPlayer.start()
                } else {
                    // If the media player is playing, reset it to the beginning
                    mediaPlayer.seekTo(0)
                }
            } else {
                // If the preference is disabled, pause the media player
                mediaPlayer.pause()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun playMedia(){

        mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + packageName + "/" + R.raw.background_music))
        mediaPlayer.prepare()
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    private fun doSongSearch(songName: String, artistName: String){
        musicMatchService.getTrackID(songName, artistName).enqueue(object : Callback<MMSongResult> {
            override fun onResponse(call: Call<MMSongResult>, response: Response<MMSongResult>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "Song Search Results: ${response.body()?.message?.mmSongBody}")
                }
            }

            override fun onFailure(call: Call<MMSongResult>, t: Throwable) {
                Log.d("MainActivity", "Error making API call: ${t.message}")
            }
        })
    }
    private fun doSongLyricSearch(trackID: String){
        musicMatchService.getSongLyrics(trackID).enqueue(object : Callback<MMLyricResult> {
            override fun onResponse(call: Call<MMLyricResult>, response: Response<MMLyricResult>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "Lyric Search Results: ${response.body()?.message?.mmLyricBody?.lyrics?.lyrics}")
                }
            }

            override fun onFailure(call: Call<MMLyricResult>, t: Throwable) {
                Log.d("MainActivity", "Error making API call: ${t.message}")
            }
        })
    }
}