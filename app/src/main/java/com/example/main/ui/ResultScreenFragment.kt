package com.example.main.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.main.R

class ResultScreenFragment : Fragment(R.layout.result_fragment) {
    private var isCorrect: Boolean = false
    private var correctAnswer: String = "placeholder"
    private val args: ResultScreenFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isCorrect = args.isCorrect
        correctAnswer = args.correctAnswer

        val correctView = view.findViewById<ImageView>(R.id.ic_guess_correct)
        val incorrectView = view.findViewById<ImageView>(R.id.ic_guess_wrong)
        if (isCorrect) {
            correctView.visibility = VISIBLE
            incorrectView.visibility = INVISIBLE
        } else {
            correctView.visibility = INVISIBLE
            incorrectView.visibility = VISIBLE
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
            else -> super.onOptionsItemSelected(item)
        }
    }
}