package com.example.main.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.main.R

class GamePlayFragment : Fragment(R.layout.game_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setDifficulty(view)
    }

    override fun onResume() {
        super.onResume()
        view?.let { view ->
            setDifficulty(view)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_nav_drawer, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val directions = GamePlayFragmentDirections.navigateToSettings()
                findNavController().navigate(directions)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setDifficulty(view: View) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val difficulty = prefs.getString(getString(R.string.pref_difficulty_key), getString(R.string.pref_difficulty_default_value))?:"not found"
        val button3 = view.findViewById<Button>(R.id.button_3)
        val button4 = view.findViewById<Button>(R.id.button_4)
        if (difficulty == getString(R.string.pref_difficulty_value_easy)) {
            button3.visibility = INVISIBLE
            button4.visibility = INVISIBLE
        }
        else if (difficulty == getString(R.string.pref_difficulty_value_medium)) {
            button3.visibility = VISIBLE
            button4.visibility = INVISIBLE
        }
        else {
            button3.visibility = VISIBLE
            button4.visibility = VISIBLE
        }
    }
}