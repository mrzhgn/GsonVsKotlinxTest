package com.example.benchmarkable.kotlinx.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MetaSerializable

@MetaSerializable
@Target(AnnotationTarget.CLASS)
@OptIn(ExperimentalSerializationApi::class)
annotation class ExamplePayloadSerializable(
    val defaultMessage: String
)