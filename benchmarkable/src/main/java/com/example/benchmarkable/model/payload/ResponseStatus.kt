package com.example.benchmarkable.model.payload

import kotlinx.serialization.SerialName
import java.io.Serializable
import kotlinx.serialization.Serializable as KSerializable

@KSerializable
enum class ResponseStatus : Serializable {

    @SerialName("Ok")
    OK,

    @SerialName("Error")
    ERROR
}