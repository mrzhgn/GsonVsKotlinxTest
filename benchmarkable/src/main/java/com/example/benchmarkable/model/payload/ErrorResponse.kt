package com.example.benchmarkable.model.payload

import com.example.benchmarkable.common.DefaultEnumValue
import com.example.benchmarkable.kotlinx.util.enumSerializerWithDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializer
import java.io.Serializable
import kotlinx.serialization.Serializable as KSerializable

@KSerializable
data class ErrorResponse(
    @SerialName("errorType")
    val errorType: ErrorType
)

@KSerializable(with = ErrorTypeSerializer::class)
enum class ErrorType : Serializable {

    @SerialName("typea")
    TYPE_A_ERROR,

    @SerialName("typeb")
    TYPE_B_ERROR,

    @DefaultEnumValue
    UNKNOWN
}

@Serializer(forClass = ResponseStatus::class)
@OptIn(ExperimentalSerializationApi::class)
object ErrorTypeSerializer :
    KSerializer<ErrorType> by enumSerializerWithDefault(default = ErrorType.UNKNOWN)