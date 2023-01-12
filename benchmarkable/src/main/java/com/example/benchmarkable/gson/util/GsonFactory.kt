package com.example.benchmarkable.gson.util

import com.example.benchmarkable.gson.serializer.OffsetDateTimeDeserializer
import com.example.benchmarkable.gson.serializer.SafeEnumTypeAdapterFactory
import com.example.benchmarkable.model.generic.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import java.time.OffsetDateTime

object GsonFactory {

    fun provideGsonInstance(): Gson {
        return GsonBuilder()
            .registerTypeAdapterFactory(SafeEnumTypeAdapterFactory())
            .registerTypeAdapterFactory(
                RuntimeTypeAdapterFactory.of(
                    BaseGenericInstance::class.java,
                    "instanceType",
                    true)
                    .registerSubtype(TypeAInstance::class.java, InstanceType.TYPE_A.jsonType)
                    .registerSubtype(TypeBInstance::class.java, InstanceType.TYPE_B.jsonType)
                    .registerSubtype(TypeCInstance::class.java, InstanceType.TYPE_C.jsonType)
            )
            .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeDeserializer())
            .create()
    }
}