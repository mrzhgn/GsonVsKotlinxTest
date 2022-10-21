package com.example.benchmarkable.model.generic

import com.example.benchmarkable.common.DefaultEnumValue
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import com.example.benchmarkable.kotlinx.util.enumSerializerWithDefault
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import java.io.Serializable
import kotlinx.serialization.Serializable as KSerializable

@KSerializable(with = InstanceTypeSerializer::class)
enum class InstanceType(val jsonType: String) : Serializable {

    @SerialName("typea")
    @SerializedName("typea")
    TYPE_A("typea"),

    @SerialName("typeb")
    @SerializedName("typeb")
    TYPE_B("typeb"),

    @SerialName("typec")
    @SerializedName("typec")
    TYPE_C("typec"),

    @DefaultEnumValue
    UNKNOWN("unknown")
}

@Serializer(forClass = InstanceType::class)
@OptIn(ExperimentalSerializationApi::class)
object InstanceTypeSerializer :
    KSerializer<InstanceType> by enumSerializerWithDefault(default = InstanceType.UNKNOWN)