package com.example.benchmarkable.model.generic

import java.io.Serializable
import kotlinx.serialization.Serializable as KSerializable

@KSerializable
data class TypeBData(
    val int1: Int,
    val int2: Int,
    val int3: Int,
    val int4: Int,
    val int5: Int,
    val int6: Int,
    val int7: Int,
    val int8: Int,
    val int9: Int,
    val int10: Int
) : Serializable, BaseInstanceData