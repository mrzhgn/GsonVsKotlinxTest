package com.example.benchmarkable.kotlinx.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Serializer(forClass = OffsetDateTime::class)
@OptIn(ExperimentalSerializationApi::class)
class OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("java.time.OffsetDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeString(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value))
    }

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        return OffsetDateTime.parse(decoder.decodeString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }
}