package com.example.main.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayerStatsDao {
    @Insert
    suspend fun insert(stat: StatsEntity)

    @Delete
    suspend fun delete(stat: StatsEntity)

    @Query("SELECT COUNT(id) FROM StatsEntity WHERE result = \'Correct\'")
    suspend fun getCountSuccess() : Int

    @Query("SELECT COUNT(id) FROM StatsEntity")
    suspend fun getTotalCount() : Int

    @Query("DELETE FROM StatsEntity")
    suspend fun clearTable()

    // Kinda hacky, but clears all auto increment values from sqlite so that
    // the id's start at 1 again
    @Query("DELETE FROM sqlite_sequence")
    suspend fun resetAutoIncrement()

    @Query("SELECT MAX(streakID) FROM StatsEntity")
    suspend fun getCurrentStreakID() : Int

    @Query("SELECT MAX(streakCount) FROM (SELECT streakID, COUNT(streakID) streakCount FROM StatsEntity WHERE result = 'Correct' GROUP BY streakID)")
    suspend fun getMaxStreakLength() : Int

    @Query("SELECT streakCount FROM ( SELECT streakID, COUNT(streakID) streakCount FROM StatsEntity WHERE result = 'Correct' GROUP BY streakID ) c WHERE c.streakID = (SELECT MAX(streakID) FROM StatsEntity)")
    suspend fun getCurrStreakLength() : Int

}