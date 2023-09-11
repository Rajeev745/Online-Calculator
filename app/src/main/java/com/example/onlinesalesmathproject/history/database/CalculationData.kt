package com.example.onlinesalesmathproject.history.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculation_data")
data class CalculationData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val calculationString: String
)