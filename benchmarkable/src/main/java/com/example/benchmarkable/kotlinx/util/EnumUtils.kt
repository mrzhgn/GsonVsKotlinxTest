package com.example.benchmarkable.kotlinx.util

import com.example.benchmarkable.kotlinx.serializer.CustomEnumSerializer

inline fun <reified E : Enum<E>> enumSerializerWithDefault(default: E? = null) =
    CustomEnumSerializer(E::class, default)