package com.example.onlinesalesmathproject.calculation

import com.google.gson.annotations.SerializedName


data class CalculationResult(
    @SerializedName("queryresult")
    val queryResult: QueryResult
)

data class QueryResult(
    val inputstring: String,
    val pods: List<Pod>
)

data class Pod(
    val subpods: List<SubPod>,
    val expressiontypes: ExpressionTypes
)

data class SubPod(
    val title: String,
    val plaintext: String
)

data class ExpressionTypes(
    val name: String
)
