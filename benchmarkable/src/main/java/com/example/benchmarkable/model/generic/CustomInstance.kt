package com.example.benchmarkable.model.generic

import com.example.benchmarkable.kotlinx.serializer.CustomInstancePolymorphicSerializer
import kotlinx.serialization.Serializable as KSerializable
import kotlinx.serialization.Transient
import java.io.Serializable

@KSerializable(with = CustomInstancePolymorphicSerializer::class)
sealed class CustomInstance<T : BaseInstanceData> : Serializable {

    abstract val instanceType: InstanceType

    abstract val data: T?
}

@KSerializable
data class TypeACustomInstance(
    override val instanceType: InstanceType,
    override val data: TypeAData?
) : CustomInstance<TypeAData>()

@KSerializable
data class TypeBCustomInstance(
    override val instanceType: InstanceType,
    override val data: TypeBData?
) : CustomInstance<TypeBData>()

@KSerializable
data class UnknownCustomInstance(
    override val instanceType: InstanceType = InstanceType.UNKNOWN,
    @Transient
    override val data: UnknownData? = null
) : CustomInstance<UnknownData>()