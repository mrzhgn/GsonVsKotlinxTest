package com.example.benchmarkable.model.generic

import java.io.Serializable
import kotlinx.serialization.Serializable as KSerializable

@KSerializable
data class TypeCData(
    val boolean1: Boolean,
    val boolean2: Boolean,
    val boolean3: Boolean,
    val boolean4: Boolean,
    val boolean5: Boolean,
    val boolean6: Boolean,
    val boolean7: Boolean,
    val boolean8: Boolean,
    val boolean9: Boolean,
    val boolean10: Boolean
) : Serializable, BaseInstanceData