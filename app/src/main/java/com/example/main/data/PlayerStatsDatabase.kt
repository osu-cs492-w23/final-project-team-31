package com.example.main.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StatsEntity::class], version = 1)
abstract class PlayerStatsDatabase : RoomDatabase() {
    abstract fun playerStatsDao() : PlayerStatsDao

    companion object {
        @Volatile private var instance: PlayerStatsDatabase? = null

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                PlayerStatsDatabase::class.java,
                "playerStats.db"
            ).build()

        fun getInstance(context: Context) : PlayerStatsDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

    }
}