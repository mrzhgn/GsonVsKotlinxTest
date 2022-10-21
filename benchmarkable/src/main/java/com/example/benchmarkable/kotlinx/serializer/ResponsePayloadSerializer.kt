package com.example.benchmarkable.kotlinx.serializer

import com.example.benchmarkable.kotlinx.util.ExamplePayloadSerializable
import com.example.benchmarkable.model.payload.ErrorResponse
import com.example.benchmarkable.model.payload.ResponsePayload
import com.example.benchmarkable.model.payload.ResponseStatus
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
        element(
            "message",
            PrimitiveSerialDescriptor("message", PrimitiveKind.STRING)
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
        val defaultMessage = descriptor.annotations
            .filterIsInstance<ExamplePayloadSerializable>()
            .first()
            .defaultMessage

        val responseMessage = jsonObject["message"]?.jsonPrimitive?.contentOrNull ?: defaultMessage

        return when (responseStatus) {
            ResponseStatus.OK -> {
                decoder.json.decodeFromJsonElement(payloadSerializer, payloadJson).let {
                    ResponsePayload(
                        payload = it,
                        status = responseStatus,
                        message = responseMessage
                    )
                }
            }
            ResponseStatus.ERROR -> {
                decoder.json.decodeFromJsonElement(ErrorResponse.serializer(), payloadJson).let {
                    ResponsePayload(
                        payload = it,
                        status = responseStatus,
                        message = responseMessage
                    )
                }
            }
            ResponseStatus.UNKNOWN -> {
                ResponsePayload(
                    payload = null,
                    status = responseStatus,
                    message = responseMessage
                )
            }
        }
    }

    private fun searchField(fieldName: String, searchIn: JsonObject): JsonElement {
        return searchIn[fieldName]
            ?: throw MissingFieldException(fieldName, descriptor.serialName) // public since v1.4.0
    }
}