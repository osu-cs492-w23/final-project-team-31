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
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.main.R
import com.example.main.api.MusixMatchService
import com.example.main.data.MusixMatch.searchLyricData.MMLyricResult
import com.example.main.data.MusixMatch.searchSongData.MMSongResult
import com.example.main.data.MusixMatch.searchSongData.MMSongTrack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class GamePlayFragment : Fragment(R.layout.game_fragment) {
    private var numButtons = 4
    private lateinit var buttonArray : Array<Button>
    private val playerStatsViewModel: PlayerStatsViewModel by viewModels()
    private val musixMatchViewModel : MusixMatchViewModel by viewModels()
    private val totalSongArray : Array<String> = arrayOf(
        "Señorita",
        "China",
        "boyfriend",
        "Beautiful People",
        "Goodbyes",
        "I Don't Care",
        "Ransom",
        "How Do You Sleep?",
        "Old Town Road - Remix",
        "bad guy",
        "Callaita",
        "Loco Contigo",
        "Someone You Loved",
        "Otro Trago - Remix",
        "Money In The Grave",
        "No Guidance",
        "LA CANCIÓN",
        "Sunflower",
        "Lalala",
        "Truth Hurts",
        "Piece Of Your Heart",
        "Panini",
        "No Me Conoce - Remix",
        "Soltera - Remix",
        "bad guy",
        "If I Can't Have You",
        "Dance Monkey",
        "It's You",
        "Con Calma",
        "QUE PRETENDES",
        "Takeaway",
        "Kill Bill",
        "Congratulations",
        "The London",
        "Never Really Over",
        "Summer Days",
        "Otro Trago",
        "Antisocial",
        "Sucker",
        "fuck, i'm lonely",
        "Flowers",
        "You Need To Calm Down",
        "Shallow",
        "Talk",
        "Con Altura",
        "One Thing Right",
        "Te Robaré",
        "Happier",
        "Call You Mine",
        "Cross Me")

    private var totalArtistsArray : Array<String> = arrayOf(
        "Shawn Mendes",
        "Anuel AA",
        "Ariana Grande",
        "Ed Sheeran",
        "Post Malone",
        "Ed Sheeran",
        "Lil Tecca",
        "Sam Smith",
        "Lil Nas X",
        "Billie Eilish",
        "Bad Bunny",
        "DJ Snake",
        "Lewis Capaldi",
        "Sech",
        "Drake",
        "Chris Brown",
        "J Balvin",
        "Post Malone",
        "Y2K",
        "Lizzo",
        "MEDUZA",
        "Lil Nas X",
        "Jhay Cortez",
        "Lunay",
        "Billie Eilish",
        "Shawn Mendes",
        "Tones and I",
        "Ali Gatie",
        "Daddy Yankee",
        "J Balvin",
        "The Chainsmokers",
        "SZA",
        "Post Malone",
        "Young Thug",
        "Katy Perry",
        "Martin Garrix",
        "Sech",
        "Ed Sheeran",
        "Jonas Brothers",
        "Lauv",
        "Miley Cyrus",
        "Taylor Swift",
        "Lady Gaga",
        "Khalid",
        "ROSALÍA",
        "Marshmello",
        "Nicky Jam",
        "Marshmello",
        "The Chainsmokers",
        "Ed Sheeran"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        // Overrides android back buttion to go back to home screen (probably not good practice idk)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val directions = GamePlayFragmentDirections.navigateToHome()
                    findNavController().navigate(directions)
                }
            }
        )

        playerStatsViewModel.getCurrStreakLength().observe(viewLifecycleOwner) {currentStreak ->
            val streakTextView = view.findViewById<TextView>(R.id.current_streak_value)
            streakTextView.text = currentStreak.toString()
        }

        setDifficulty(view)
        val songChoiceArray : Array<Int> = arrayOf(-1,-1,-1,-1)
        for (i in 0..3) {
            var newint = Random.nextInt(0,50)
            while (songChoiceArray.contains(newint)) {
                newint = Random.nextInt(0,50)
            }
            songChoiceArray[i] = newint
        }

//        for (i in 0..49) {
//            musixMatchViewModel.getSongData(totalSongArray[i], totalArtistsArray[i])
//        }

        // Call spotify API here
        val buttonStringArray : Array<String> = arrayOf(
            totalSongArray[songChoiceArray[0]],
            totalSongArray[songChoiceArray[1]],
            totalSongArray[songChoiceArray[2]],
            totalSongArray[songChoiceArray[3]])
        val buttonArtistArray : Array<String> = arrayOf(
            totalArtistsArray[songChoiceArray[0]],
            totalArtistsArray[songChoiceArray[1]],
            totalArtistsArray[songChoiceArray[2]],
            totalArtistsArray[songChoiceArray[3]])

        buttonArray = arrayOf(
            view.findViewById(R.id.button_1),
            view.findViewById(R.id.button_2),
            view.findViewById(R.id.button_3),
            view.findViewById(R.id.button_4)
        )

        val correctIdx = Random.nextInt(0, numButtons)
        val correctButton = buttonArray[correctIdx]
        val correctString = buttonStringArray[correctIdx]
        val correctArtist = buttonArtistArray[correctIdx]

        // Call lyrics API here
        val songLyricsTextView = view.findViewById<TextView>(R.id.tweet_text)
        musixMatchViewModel.getSongData(correctString, correctArtist)

        musixMatchViewModel.songData.observe(viewLifecycleOwner) { result ->
            Log.d(tag, "Observed change in songData")
            val trackList = result?.message?.mmSongBody?.trackList
            if (trackList != null) {
                val hasLyrics = trackList[0].track.has_lyrics
                val songID = trackList[0].track.songID
                Log.d(tag, "Received songID of $songID, songName ${trackList[0].track.songName}")
                if(hasLyrics == 1) {
                   musixMatchViewModel.getLyricData(songID)
                } else {
                    Log.d(tag, "Song ${trackList[0].track.songName} does not have lyrics")
                    songLyricsTextView.text = "Lyrics not available"
                }
            }
        }

        musixMatchViewModel.lyricData.observe(viewLifecycleOwner) {result ->
            Log.d(tag, "Observed change in lyricData")
            val lyrics = result?.message?.mmLyricBody?.lyrics?.lyrics?:"Loading..."
            Log.d(tag, "Received lyricData of $lyrics")
            songLyricsTextView.text = lyrics.take(350)
        }


        Log.d(tag,"Correct button is $correctString")

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