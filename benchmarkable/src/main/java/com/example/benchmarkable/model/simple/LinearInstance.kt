package com.example.benchmarkable.model.simple

import kotlinx.serialization.Serializable as KSerializable
import java.io.Serializable

@KSerializable
data class LinearInstance(
    val string1: String,
    val string2: String,
    val string3: String,
    val string4: String,
    val string5: String,
    val string6: String,
    val string7: String,
    val string8: String,
    val string9: String,
    val string10: String,
    val int1: Int,
    val int2: Int,
    val int3: Int,
    val int4: Int,
    val int5: Int,
    val int6: Int,
    val int7: Int,
    val int8: Int,
    val int9: Int,
    val int10: Int,
    val boolean1: Boolean,
    val boolean2: Boolean,
    val boolean3: Boolean,
    val boolean4: Boolean,
    val boolean5: Boolean,
    val boolean6: Boolean,
    val boolean7: Boolean,
    val boolean8: Boolean,
    val boolean9: Boolean,
    val boolean10: Boolean,
) : Serializable