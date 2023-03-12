package com.example.benchmarkable.kotlinx.serializer

import android.util.Log
import com.example.benchmarkable.model.generic.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.nullable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

@Serializer(forClass = CustomInstance::class)
@OptIn(ExperimentalSerializationApi::class)
class CustomInstancePolymorphicSerializer<T : BaseInstanceData>(
    private val dataSerializer: KSerializer<T>,
) : KSerializer<CustomInstance<T>> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        "com.example.benchmarkable.model.generic.CustomInstance"
    ) {
        element(
            "instanceType",
            PrimitiveSerialDescriptor("instanceType", PrimitiveKind.STRING)
        )
        element(
            "instanceData",
            dataSerializer.descriptor.nullable,
            isOptional = true
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(decoder: Decoder): CustomInstance<T> {
        val input = decoder as? JsonDecoder
            ?: throw SerializationException("Expected JsonDecoder for ${decoder::class}")
        val jsonObject = input.decodeJsonElement().jsonObject

        val instanceType = searchField("instanceType", jsonObject)

        val serializer = when (decoder.json.decodeFromJsonElement(InstanceType.serializer(), instanceType)) {
            InstanceType.TYPE_A -> TypeACustomInstance.serializer()
            InstanceType.TYPE_B -> TypeBCustomInstance.serializer()
            InstanceType.UNKNOWN -> {
                Log.d("PARSING", "Unexpected instance type: $instanceType")
                UnknownCustomInstance.serializer()
            }
            else -> UnknownCustomInstance.serializer()
        }

        return decoder.json.decodeFromString(serializer, jsonObject.toString()) as CustomInstance<T>
    }

    override fun serialize(encoder: Encoder, value: CustomInstance<T>) {
        when (value) {
            is TypeACustomInstance -> TypeACustomInstance.serializer().serialize(encoder, value)
            is TypeBCustomInstance -> TypeBCustomInstance.serializer().serialize(encoder, value)
            is UnknownCustomInstance -> UnknownCustomInstance.serializer().serialize(encoder, value)
        }
    }

    private fun searchField(fieldName: String, searchIn: JsonObject): JsonElement {
        return searchIn[fieldName]
            ?: throw MissingFieldException(fieldName, descriptor.serialName) // public since v1.4.0
    }
}