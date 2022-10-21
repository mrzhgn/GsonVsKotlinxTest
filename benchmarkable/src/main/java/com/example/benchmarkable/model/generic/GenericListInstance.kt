package com.example.benchmarkable.model.generic

import kotlinx.serialization.Serializable as KSerializable
import java.io.Serializable

@KSerializable
data class GenericListInstance(
    val items: List<GenericContainerInstance>,
) : Serializable

@KSerializable
data class GenericContainerInstance(
    val testItem: BaseGenericInstance<*>
) : Serializable