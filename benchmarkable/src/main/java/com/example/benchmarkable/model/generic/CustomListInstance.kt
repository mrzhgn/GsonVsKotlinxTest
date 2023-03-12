package com.example.benchmarkable.model.generic

import kotlinx.serialization.Serializable as KSerializable
import java.io.Serializable

@KSerializable
data class CustomListInstance(
    val items: List<CustomContainerInstance>,
) : Serializable

@KSerializable
data class CustomContainerInstance(
    val testItem: CustomInstance<*>
) : Serializable