@file:UseContextualSerialization(forClasses = [BigDecimal::class, OffsetDateTime::class])
package com.example.benchmarkable.model.contextual

import kotlinx.serialization.UseContextualSerialization
import kotlinx.serialization.Serializable as KSerializable
import java.io.Serializable
import java.math.BigDecimal
import java.time.OffsetDateTime

@KSerializable
data class ContextualInstance(
    val float1: BigDecimal,
    val float2: BigDecimal,
    val float3: BigDecimal,
    val float4: BigDecimal,
    val float5: BigDecimal,
    val date1: OffsetDateTime,
    val date2: OffsetDateTime,
    val date3: OffsetDateTime,
    val date4: OffsetDateTime,
    val date5: OffsetDateTime,
    val float6: BigDecimal,
    val float7: BigDecimal,
    val float8: BigDecimal,
    val float9: BigDecimal,
    val float10: BigDecimal,
    val date6: OffsetDateTime,
    val date7: OffsetDateTime,
    val date8: OffsetDateTime,
    val date9: OffsetDateTime,
    val date10: OffsetDateTime,
    val float11: BigDecimal,
    val float12: BigDecimal,
    val float13: BigDecimal,
    val float14: BigDecimal,
    val float15: BigDecimal,
    val date11: OffsetDateTime,
    val date12: OffsetDateTime,
    val date13: OffsetDateTime,
    val date14: OffsetDateTime,
    val date15: OffsetDateTime
) : Serializable