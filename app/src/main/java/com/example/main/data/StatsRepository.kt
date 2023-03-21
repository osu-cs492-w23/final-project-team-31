package com.example.main.data

class StatsRepository(
    private val dao: PlayerStatsDao
) {
    suspend fun insertStat(stat: StatsEntity) = dao.insert(stat)

    suspend fun deleteStat(stat: StatsEntity) = dao.delete(stat)

    suspend fun getCountSuccess() = dao.getCountSuccess()

    suspend fun getTotalCount() = dao.getTotalCount()

    suspend fun wipeDatabase() = dao.clearTable()

    suspend fun resetAutoIncrement() = dao.resetAutoIncrement()

    suspend fun getCurrentStreakID() = dao.getCurrentStreakID()

    suspend fun getMaxStreakLength() = dao.getMaxStreakLength()

    suspend fun getCurrStreakLength() = dao.getCurrStreakLength()
}