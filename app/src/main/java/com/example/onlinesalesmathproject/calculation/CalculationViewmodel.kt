package com.example.onlinesalesmathproject.calculation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinesalesmathproject.utils.CONSTANTS
import com.example.onlinesalesmathproject.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class CalculationViewmodel @Inject constructor(private val retrofit: Retrofit) :
    ViewModel() {

    private val TAG = "CalculationViewmodel"

    private val _getResult =
        MutableStateFlow<Resource<QueryResult>>(Resource.Unspecified())
    val getResult = _getResult.asStateFlow()

    fun getResultData(expression: String) {

        viewModelScope.launch(Dispatchers.IO) {
            _getResult.emit(Resource.Loading())
            try {
                val apiService = retrofit.create(ExpressionRequest::class.java)
                val getResponse =
                    apiService.evaluateResults(
                        expression,
                        "plaintext",
                        "JSON",
                        CONSTANTS.APPLICATION_ID
                    )

                _getResult.emit(Resource.Success(getResponse.queryResult))

            } catch (e: Exception) {
                e.printStackTrace()
                _getResult.emit(Resource.Error(e.message))
            }
        }

    }
}

