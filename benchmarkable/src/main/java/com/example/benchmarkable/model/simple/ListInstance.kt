package com.example.benchmarkable.model.simple

import kotlinx.serialization.Serializable as KSerializable
import java.io.Serializable

@KSerializable
data class ListInstance(
    val items: List<ContainerInstance>,
) : Serializable

@KSerializable
data class ContainerInstance(
    val testItem: SimpleInstance
) : Serializable
