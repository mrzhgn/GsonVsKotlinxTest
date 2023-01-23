package com.example.benchmarkable.kotlinx.serializer

import com.example.benchmarkable.model.payload.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

@Serializer(forClass = ResponsePayload::class)
@OptIn(ExperimentalSerializationApi::class)
class ResponsePayloadSerializer<T>(private val payloadSerializer: KSerializer<T>) :
    KSerializer<ResponsePayload<*>> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        "com.example.benchmarkable.model.payload.ResponsePayload"
    ) {
        annotations = ResponsePayload::class.annotations
        element(
            "payload",
            payloadSerializer.descriptor
        )
        element(
            "status",
            PrimitiveSerialDescriptor("status", PrimitiveKind.STRING)
        )
    }

    override fun serialize(encoder: Encoder, value: ResponsePayload<*>) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): ResponsePayload<*> {
        val input = decoder as? JsonDecoder
            ?: throw SerializationException("Expected JsonDecoder for ${decoder::class}")
        val jsonObject = input.decodeJsonElement().jsonObject

        val payloadJson = searchField("payload", jsonObject)
        val responseStatus = decoder.json.decodeFromJsonElement(
            ResponseStatus.serializer(),
            searchField("status", jsonObject)
        )

        return when (responseStatus) {
            ResponseStatus.OK -> {
                decoder.json.decodeFromJsonElement(payloadSerializer, payloadJson).let {
                    SuccessPayload(
                        payload = it,
                        status = responseStatus
                    )
                }
            }
            ResponseStatus.ERROR -> {
                decoder.json.decodeFromJsonElement(ErrorResponse.serializer(), payloadJson).let {
                    ErrorPayload(
                        payload = it,
                        status = responseStatus
                    )
                }
            }
        }
    }

    private fun searchField(fieldName: String, searchIn: JsonObject): JsonElement {
        return searchIn[fieldName]
            ?: throw MissingFieldException(fieldName, descriptor.serialName) // public since v1.4.0
    }
}