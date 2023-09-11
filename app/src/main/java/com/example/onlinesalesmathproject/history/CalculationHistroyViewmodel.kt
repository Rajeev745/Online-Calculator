package com.example.onlinesalesmathproject.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.onlinesalesmathproject.history.database.CalculationData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculationHistroyViewmodel @Inject constructor(
    private val historyRepository: CalculationHistoryRepository
) : ViewModel() {

    val allCalculations: LiveData<List<CalculationData>> = historyRepository.allCalculations

    suspend fun insert(calculationData: CalculationData) {
        historyRepository.insert(calculationData)
    }

}