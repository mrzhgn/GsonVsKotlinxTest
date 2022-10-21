package com.example.benchmarkable.gson.serializer

import com.example.benchmarkable.common.DefaultEnumValue
import com.google.gson.TypeAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class SafeEnumTypeAdapter<T : Enum<*>>(classOfT: Class<T>) : TypeAdapter<T?>() {

    private val nameToConstant: MutableMap<String, T> = HashMap()
    private val constantToName: MutableMap<T, String> = HashMap()
    private var defaultValue: T? = null

    init {
        val enumConstants = classOfT.enumConstants
        if (enumConstants != null) {
            for (constant in enumConstants) {
                val name = constant!!.name
                val serialNameAnnotation = constant.getFieldAnnotation<SerializedName>()
                val defaultEnumValueAnnotation = constant.getFieldAnnotation<DefaultEnumValue>()
                if (defaultEnumValueAnnotation != null) {
                    defaultValue = constant
                }
                if (serialNameAnnotation != null) {
                    for (alternate in serialNameAnnotation.alternate) {
                        nameToConstant[alternate] = constant
                    }
                    nameToConstant[serialNameAnnotation.value] = constant
                    constantToName[constant] = serialNameAnnotation.value
                } else {
                    nameToConstant[name] = constant
                    constantToName[constant] = name
                }
            }
        }
    }

    override fun read(reader: JsonReader): T? {
        return nameToConstant[reader.nextString()] ?: defaultValue
    }

    override fun write(out: JsonWriter, value: T?) {
        out.value(constantToName[value])
    }

    private inline fun <reified A : Annotation> Enum<*>.getFieldAnnotation(): A? =
        javaClass.getDeclaredField(name).getAnnotation(A::class.java)
}