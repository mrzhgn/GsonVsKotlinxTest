package com.example.benchmarkable.model.payload

import com.example.benchmarkable.common.DefaultEnumValue
import com.example.benchmarkable.kotlinx.util.enumSerializerWithDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializer
import java.io.Serializable
import kotlinx.serialization.Serializable as KSerializable

@KSerializable(with = ResponseStatusSerializer::class)
enum class ResponseStatus : Serializable {

    @SerialName("Ok")
    OK,

    @SerialName("Error")
    ERROR,

    @DefaultEnumValue
    UNKNOWN
}

@Serializer(forClass = ResponseStatus::class)
@OptIn(ExperimentalSerializationApi::class)
object ResponseStatusSerializer :
        KSerializer<ResponseStatus> by enumSerializerWithDefault(default = ResponseStatus.UNKNOWN)