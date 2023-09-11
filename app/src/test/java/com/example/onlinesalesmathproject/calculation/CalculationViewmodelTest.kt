package com.example.onlinesalesmathproject.calculation

import com.example.onlinesalesmathproject.utils.CONSTANTS
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create

class CalculationViewmodelTest {

    private lateinit var calculationViewmodel: CalculationViewmodel

    @Mock
    private lateinit var retrofit: Retrofit

    @Mock
    private lateinit var mockExpressionRequest: ExpressionRequest

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        calculationViewmodel = CalculationViewmodel(retrofit)
        mockExpressionRequest = mock(ExpressionRequest::class.java)
    }

    @Test
    fun `get data for success`(): Unit = runBlocking {
        val expression = "2+2"
        val successResponse = mock(CalculationResult::class.java)

        `when`(retrofit.create(ExpressionRequest::class.java)).then(mockExpressionRequest)
        `when`(mockExpressionRequest.evaluateResults(expression, "plaintext", "JSON", CONSTANTS.APPLICATION_ID))
            .thenReturn(successResponse)

    }

    @After
    fun cleanUp() {

    }
}