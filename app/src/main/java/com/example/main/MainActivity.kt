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

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfig: AppBarConfiguration
    private val mediaPlayer = MediaPlayer()

    private fun playMedia(){
        mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + packageName + "/" + R.raw.background_music))
        mediaPlayer.prepare()
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

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


        playMedia()
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

}