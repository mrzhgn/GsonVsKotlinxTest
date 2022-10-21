package com.example.benchmarkable.model.payload

import com.example.benchmarkable.kotlinx.util.ExamplePayloadSerializable
import kotlinx.serialization.SerialName

@ExamplePayloadSerializable(defaultMessage = "Some default message")
data class ResponsePayload<T>(
    @SerialName("payload")
    val payload: T? = null,
    @SerialName("status")
    val status: ResponseStatus = ResponseStatus.UNKNOWN,
    @SerialName("message")
    val message: String
)