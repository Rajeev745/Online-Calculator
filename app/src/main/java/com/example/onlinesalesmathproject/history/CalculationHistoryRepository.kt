package com.example.onlinesalesmathproject.history

import androidx.lifecycle.LiveData
import com.example.onlinesalesmathproject.history.database.CalculationDao
import com.example.onlinesalesmathproject.history.database.CalculationData
import javax.inject.Inject

class CalculationHistoryRepository @Inject constructor(private val calculationDao: CalculationDao) {

    val allCalculations: LiveData<List<CalculationData>> = calculationDao.getAllCalculations()

    suspend fun insert(calculationData: CalculationData) {
        calculationDao.insert(calculationData)
    }
}