package com.example.onlinesalesmathproject.history.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CalculationData::class], version = 1)
abstract class CalculationDatabase : RoomDatabase() {

    abstract fun calculationDao(): CalculationDao
}