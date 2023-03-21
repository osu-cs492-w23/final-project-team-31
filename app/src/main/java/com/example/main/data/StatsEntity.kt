package com.example.main.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StatsEntity(
    val result : String,
    val streakID : Int
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
