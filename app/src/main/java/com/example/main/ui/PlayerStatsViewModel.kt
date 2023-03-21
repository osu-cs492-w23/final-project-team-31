package com.example.main.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.main.data.PlayerStatsDatabase
import com.example.main.data.StatsEntity
import com.example.main.data.StatsRepository
import kotlinx.coroutines.launch

class PlayerStatsViewModel(application: Application) : AndroidViewModel(application) {
    private var repository = StatsRepository(
        PlayerStatsDatabase.getInstance(application).playerStatsDao()
    )

    private var currentStreakNum : Int = 0

    init {
        getCurrentStreakID()
    }

    fun addStat(statEntry: String) {
        viewModelScope.launch {
            if (statEntry == "Incorrect") {
                currentStreakNum++
            }
            repository.insertStat(StatsEntity(statEntry, currentStreakNum))
        }
    }

    fun removeStat(stat: StatsEntity) {
        viewModelScope.launch {
            repository.deleteStat(stat)
        }
    }

    fun getSuccessRate() : LiveData<Double> {
        val result = MutableLiveData<Double>()
        viewModelScope.launch {
            val numSuccess = repository.getCountSuccess()
            val totalCount = repository.getTotalCount()
            var returned = numSuccess.toDouble()/totalCount
            if(totalCount == 0)
                returned = 0.0
            result.postValue(returned)
        }
        return result
    }

    fun wipeDatabase() {
        viewModelScope.launch {
            repository.wipeDatabase()
            repository.resetAutoIncrement()
            currentStreakNum = 0
        }
    }

    fun getCurrentStreakID() : LiveData<Int> {
        val result = MutableLiveData<Int>()
        viewModelScope.launch {
            val returned = repository.getCurrentStreakID()
            result.postValue(returned)
            currentStreakNum = returned
        }
        return result
    }

    fun getMaxStreakLength() : LiveData<Int> {
        val result = MutableLiveData<Int>()
        viewModelScope.launch {
            val returned = repository.getMaxStreakLength()
            result.postValue(returned)
        }
        return result
    }

    fun getCurrStreakLength() : LiveData<Int> {
        val result = MutableLiveData<Int>()
        viewModelScope.launch {
            val returned = repository.getCurrStreakLength()
            result.postValue(returned)
        }
        return result
    }
}