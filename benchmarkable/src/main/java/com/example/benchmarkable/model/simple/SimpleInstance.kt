package com.example.benchmarkable.model.simple

import kotlinx.serialization.Serializable as KSerializable
import java.io.Serializable

@KSerializable
data class SimpleInstance(
    val string1: String,
    val string2: String,
    val string3: String,
    val string4: String,
    val string5: String,
    val string6: String,
    val string7: String,
    val string8: String,
    val string9: String,
    val string10: String
) : Serializable