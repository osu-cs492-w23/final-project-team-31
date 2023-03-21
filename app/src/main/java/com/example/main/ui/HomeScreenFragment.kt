package com.example.main.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.main.R

class HomeScreenFragment : Fragment(R.layout.menu_fragment) {
    private val playerStatsViewModel: PlayerStatsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        playerStatsViewModel.getSuccessRate().observe(viewLifecycleOwner) {successRate ->
            val accTextView = view.findViewById<TextView>(R.id.accuracy_value)
            accTextView.text = getString(R.string.display_percent, successRate*100)
        }

        playerStatsViewModel.getMaxStreakLength().observe(viewLifecycleOwner) {maxStreak ->
            val streakTextView = view.findViewById<TextView>(R.id.highest_streak_value)
            streakTextView.text = maxStreak.toString()
        }

        val playButton : ImageButton = view.findViewById(R.id.play_button)
        playButton.setOnClickListener {
            val directions = HomeScreenFragmentDirections.navigateToGameplay()
            findNavController().navigate(directions)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_nav_drawer, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val directions = HomeScreenFragmentDirections.navigateToSettings()
                findNavController().navigate(directions)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}