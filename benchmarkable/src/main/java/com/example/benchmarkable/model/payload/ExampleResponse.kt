package com.example.benchmarkable.model.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable as KSerializable

@KSerializable
data class ExampleResponse(
    @SerialName("someData")
    val someData: String
)