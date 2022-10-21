package com.example.benchmarkable.kotlinx.serializer

import com.example.benchmarkable.common.DefaultEnumValue
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonPrimitive
import kotlin.reflect.KClass

@OptIn(ExperimentalSerializationApi::class)
class CustomEnumSerializer<E : Enum<E>>(
    private val kClass: KClass<E>,
    private val fallback: E? = null
) : KSerializer<E> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            kClass.java.canonicalName ?: kClass.toString(),
            PrimitiveKind.STRING
        )

    override fun serialize(encoder: Encoder, value: E) {
        val name = value.getFieldAnnotation<SerialName>()?.value ?: value.name
        encoder.encodeString(name)
    }

    override fun deserialize(decoder: Decoder): E {
        return (decoder as JsonDecoder).decodeJsonElement().jsonPrimitive
            .let { jsonPrimitive ->
                if (jsonPrimitive.isString) {
                    toEnum(jsonPrimitive.content)
                } else {
                    throw IllegalStateException(
                        "$jsonPrimitive is not string"
                    )
                }
            }
    }

    private fun toEnum(value: String): E {
        val enumConstants = kClass.java.enumConstants
        return enumConstants
            ?.firstOrNull {
                val fieldAnnotation = it.getFieldAnnotation<SerialName>()
                when {
                    fieldAnnotation != null -> fieldAnnotation.value == value
                    else -> it.name == value
                }
            }
            ?: fallback
            ?: enumConstants?.find { it.getFieldAnnotation<DefaultEnumValue>() != null }
            ?: throw IllegalStateException(
                "${descriptor.serialName} does not contain element with name '$value'"
            )
    }

    private inline fun <reified A : Annotation> Enum<*>.getFieldAnnotation(): A? =
        javaClass.getDeclaredField(name).getAnnotation(A::class.java)
}
