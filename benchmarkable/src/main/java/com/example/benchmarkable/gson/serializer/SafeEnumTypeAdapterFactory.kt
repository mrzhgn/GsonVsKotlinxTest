package com.example.benchmarkable.gson.serializer

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken

@Suppress("UNCHECKED_CAST")
class SafeEnumTypeAdapterFactory : TypeAdapterFactory {

    override fun <T : Any> create(gson: Gson, typeToken: TypeToken<T>): TypeAdapter<T>? {
        val rawType = typeToken.rawType
        if (!Enum::class.java.isAssignableFrom(rawType) || rawType == Enum::class.java) {
            return null
        }
        return SafeEnumTypeAdapter(rawType as Class<Enum<*>>).nullSafe() as TypeAdapter<T>
    }
}