package com.example.benchmarkable.gson.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class OffsetDateTimeDeserializer : JsonDeserializer<OffsetDateTime?> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): OffsetDateTime? {
        return when {
            json == null -> null
            json.isJsonPrimitive -> {
                val primitive = json.asJsonPrimitive
                if (primitive.isString) {
                    OffsetDateTime.parse(json.asString, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                } else null
            }
            else -> null
        }
    }
}