package com.example.main.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.main.R
import kotlin.random.Random

class GamePlayFragment : Fragment(R.layout.game_fragment) {
    private var numButtons = 4
    private lateinit var buttonArray : Array<Button>
    private val playerStatsViewModel: PlayerStatsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        playerStatsViewModel.getCurrStreakLength().observe(viewLifecycleOwner) {currentStreak ->
            val streakTextView = view.findViewById<TextView>(R.id.current_streak_value)
            streakTextView.text = currentStreak.toString()
        }

        setDifficulty(view)

        // Do API calls and get data to fill here
        val buttonStringArray : Array<String> = arrayOf( "button 1", "button 2", "button 3", "button 4")

        buttonArray = arrayOf(
            view.findViewById(R.id.button_1),
            view.findViewById(R.id.button_2),
            view.findViewById(R.id.button_3),
            view.findViewById(R.id.button_4)
        )

        val correctIdx = Random.nextInt(0, numButtons)
        val correctButton = buttonArray[correctIdx]
        val correctString = buttonStringArray[correctIdx]
        Log.d(tag,"Correct button is ${correctString}")

        // Set listener for button 1
        buttonArray[0].text = buttonStringArray[0]
        buttonArray[0].setOnClickListener {
            playerStatsViewModel.addStat(getString(R.string.db_val_incorrect))
            val directions = GamePlayFragmentDirections.navigateToResult(correctString)
            findNavController().navigate(directions)
        }

        // Set listener for button 2
        buttonArray[1].text = buttonStringArray[1]
        buttonArray[1].setOnClickListener {
            playerStatsViewModel.addStat(getString(R.string.db_val_incorrect))
            val directions = GamePlayFragmentDirections.navigateToResult(correctString)
            findNavController().navigate(directions)
        }

        // Set listener for button 3
        if (numButtons > 2) {
            buttonArray[2].text = buttonStringArray[2]
            buttonArray[2].setOnClickListener {
                playerStatsViewModel.addStat(getString(R.string.db_val_incorrect))
                val directions = GamePlayFragmentDirections.navigateToResult(correctString)
                findNavController().navigate(directions)
            }
        }

        // Set listener for button 4
        if (numButtons > 3) {
            buttonArray[3].text = buttonStringArray[3]
            buttonArray[3].setOnClickListener {
                playerStatsViewModel.addStat(getString(R.string.db_val_incorrect))
                val directions = GamePlayFragmentDirections.navigateToResult(correctString)
                findNavController().navigate(directions)
            }
        }

        // Set listener for the correct choice
        correctButton.text = "correct"
        correctButton.setOnClickListener {
            playerStatsViewModel.addStat(getString(R.string.db_val_correct))
            val directions = GamePlayFragmentDirections.navigateToResult(correctString, true)
            findNavController().navigate(directions)
        }


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
            android.R.id.home -> {
                val directions = GamePlayFragmentDirections.navigateToHome()
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
            numButtons = 2
        }
        else if (difficulty == getString(R.string.pref_difficulty_value_medium)) {
            button3.visibility = VISIBLE
            button4.visibility = INVISIBLE
            numButtons = 3
        }
        else {
            button3.visibility = VISIBLE
            button4.visibility = VISIBLE
            numButtons = 4
        }
    }
}