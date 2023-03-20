package com.example.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.media.MediaPlayer
import android.net.Uri
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfig: AppBarConfiguration
    private val mediaPlayer = MediaPlayer()

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
        sharedPref.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == getString(R.string.pref_audio_key)) {
                val enableAudio = sharedPref.getBoolean(key, true)
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

        // Start playing the media if the preference is enabled
        if (sharedPref.getBoolean(getString(R.string.pref_audio_key), true)) {
            playMedia()
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

}