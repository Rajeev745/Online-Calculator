package com.example.onlinesalesmathproject

import android.content.Context
import androidx.room.Room
import com.example.onlinesalesmathproject.calculation.ExpressionRequest
import com.example.onlinesalesmathproject.history.CalculationHistoryRepository
import com.example.onlinesalesmathproject.history.CalculationHistroyViewmodel
import com.example.onlinesalesmathproject.history.database.CalculationDao
import com.example.onlinesalesmathproject.history.database.CalculationDatabase
import com.example.onlinesalesmathproject.utils.CONSTANTS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CONSTANTS.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWolframAlphaService(retrofit: Retrofit): ExpressionRequest {
        return retrofit.create(ExpressionRequest::class.java)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): CalculationDatabase {
        return Room.databaseBuilder(context, CalculationDatabase::class.java, CONSTANTS.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideStringItemDao(database: CalculationDatabase): CalculationDao {
        return database.calculationDao()
    }

    @Provides
    @Singleton
    fun provideStringItemRepository(dao: CalculationDao): CalculationHistoryRepository {
        return CalculationHistoryRepository(dao)
    }

    @Provides
    @Singleton
    fun provideStringItemViewModel(repository: CalculationHistoryRepository): CalculationHistroyViewmodel {
        return CalculationHistroyViewmodel(repository)
    }


}