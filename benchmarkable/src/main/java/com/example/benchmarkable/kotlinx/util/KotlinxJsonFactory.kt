package com.example.benchmarkable.kotlinx.util

import com.example.benchmarkable.kotlinx.serializer.BigDecimalSerializer
import com.example.benchmarkable.kotlinx.serializer.OffsetDateTimeSerializer
import com.example.benchmarkable.model.generic.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.overwriteWith
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.math.BigDecimal
import java.time.OffsetDateTime

object KotlinxJsonFactory {

    @OptIn(ExperimentalSerializationApi::class)
    fun provideKotlinxJsonInstance(): Json {
        return Json {
            serializersModule = serializersModule.overwriteWith(
                SerializersModule {
                    contextual(BigDecimal::class, BigDecimalSerializer())
                    contextual(OffsetDateTime::class, OffsetDateTimeSerializer())
                    polymorphic(BaseGenericInstance::class) {
                        subclass(TypeAInstance::class)
                        subclass(TypeBInstance::class)
                        subclass(TypeCInstance::class)
                        default { UnknownInstance.serializer() }
                    }
                }
            )
            coerceInputValues = true
            ignoreUnknownKeys = true
            explicitNulls = false
            isLenient = true
        }
    }
}