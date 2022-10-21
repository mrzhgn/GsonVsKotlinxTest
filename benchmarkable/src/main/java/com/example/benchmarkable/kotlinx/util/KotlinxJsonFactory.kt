package com.example.benchmarkable.kotlinx.util

import com.example.benchmarkable.kotlinx.serializer.BigDecimalSerializer
import com.example.benchmarkable.kotlinx.serializer.OffsetDateTimeSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.overwriteWith
import java.math.BigDecimal
import java.time.OffsetDateTime

class KotlinxJsonFactory {

    companion object {

        @OptIn(ExperimentalSerializationApi::class)
        fun provideKotlinxJsonInstance(): Json {
            return Json {
                serializersModule = serializersModule.overwriteWith(
                    SerializersModule {
                        contextual(BigDecimal::class, BigDecimalSerializer())
                        contextual(OffsetDateTime::class, OffsetDateTimeSerializer())
                    }
                )
                coerceInputValues = true
                ignoreUnknownKeys = true
                explicitNulls = false
                isLenient = true
            }
        }
    }
}