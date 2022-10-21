package com.example.benchmarkable.model.simple

import kotlinx.serialization.Serializable as KSerializable
import java.io.Serializable

@KSerializable
data class NestingInstance(
    val string: String,
    val data: NestingInstanceData
) : Serializable

@KSerializable
data class NestingInstanceData(
    val string1: String,
    val string2: String,
    val data1: NestingInstanceInnerData,
    val data2: NestingInstanceInnerData
) : Serializable

@KSerializable
data class NestingInstanceInnerData(
    val obj1: InnerDataObj,
    val obj2: InnerDataObj,
    val obj3: InnerDataObj,
) : Serializable

@KSerializable
data class InnerDataObj(
    val string: String,
    val int: Int,
    val boolean: Boolean
)