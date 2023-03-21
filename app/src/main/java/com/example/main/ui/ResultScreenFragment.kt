package com.example.main.ui

import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.main.R

class ResultScreenFragment : Fragment(R.layout.result_fragment) {
    private var isCorrect: Boolean = false
    private var correctAnswer: String = "placeholder"
    private val args: ResultScreenFragmentArgs by navArgs()
    private val playerStatsViewModel: PlayerStatsViewModel by viewModels()
    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0
    private val mediaPlayer = MediaPlayer()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isCorrect = args.isCorrect
        correctAnswer = args.correctAnswer

        playerStatsViewModel.getCurrStreakLength().observe(viewLifecycleOwner) {currentStreak ->
            val streakTextView = view.findViewById<TextView>(R.id.current_streak_value)
            streakTextView.text = currentStreak.toString()
        }

        // Overrides android back button to go back to game screen (probably not good practice idk)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val directions = ResultScreenFragmentDirections.navigateToGameplay()
                    findNavController().navigate(directions)
                }
            }
        )



        val correctView = view.findViewById<ImageView>(R.id.ic_guess_correct)
        val incorrectView = view.findViewById<ImageView>(R.id.ic_guess_wrong)
        val correctText = view.findViewById<TextView>(R.id.text_guess_correct)
        val incorrectText = view.findViewById<TextView>(R.id.text_guess_wrong)
        if (isCorrect) {
            val mediaPlayer = MediaPlayer.create(context, R.raw.negative)
            mediaPlayer.start()
            correctView.visibility = VISIBLE
            incorrectView.visibility = INVISIBLE
            correctText.visibility = VISIBLE
            incorrectText.visibility = INVISIBLE
        } else {
            val mediaPlayer = MediaPlayer.create(context, R.raw.positive)
            mediaPlayer.start()
            correctView.visibility = INVISIBLE
            incorrectView.visibility = VISIBLE
            correctText.visibility = INVISIBLE
            incorrectText.visibility = VISIBLE
        }
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val playButton : Button = view.findViewById(R.id.button_next)
        playButton.setOnClickListener {
            val directions = ResultScreenFragmentDirections.navigateToGameplay()
            findNavController().navigate(directions)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_nav_drawer, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val directions = ResultScreenFragmentDirections.navigateToSettings()
                findNavController().navigate(directions)
                true
            }
            android.R.id.home -> {
                val directions = ResultScreenFragmentDirections.navigateToGameplay()
                findNavController().navigate(directions)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}