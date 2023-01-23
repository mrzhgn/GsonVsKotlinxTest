package com.example.benchmarkable.model.payload

import com.example.benchmarkable.kotlinx.serializer.ResponsePayloadSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable as KSerializable

@KSerializable(with = ResponsePayloadSerializer::class)
sealed class ResponsePayload<T> {

    abstract val payload: T

    abstract val status: ResponseStatus
}

@KSerializable
data class ErrorPayload(
    @SerialName("payload")
    override val payload: ErrorResponse,
    @SerialName("status")
    override val status: ResponseStatus = ResponseStatus.ERROR
) : ResponsePayload<ErrorResponse>()

@KSerializable
data class SuccessPayload<T>(
    @SerialName("payload")
    override val payload: T,
    @SerialName("status")
    override val status: ResponseStatus = ResponseStatus.OK
) : ResponsePayload<T>()