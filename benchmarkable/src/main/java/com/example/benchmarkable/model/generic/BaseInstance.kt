package com.example.benchmarkable.model.generic

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable as KSerializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.JsonClassDiscriminator
import java.io.Serializable

@KSerializable
@JsonClassDiscriminator(discriminator = "instanceType")
@OptIn(ExperimentalSerializationApi::class)
sealed class BaseInstance<T : BaseInstanceData> : Serializable {

    abstract val instanceType: InstanceType

    abstract val data: T?
}

@KSerializable
@SerialName("typea")
data class TypeAInstance(
    @Transient
    override val instanceType: InstanceType = InstanceType.TYPE_A,
    override val data: TypeAData?
) : BaseInstance<TypeAData>()

@KSerializable
@SerialName("typeb")
data class TypeBInstance(
    @Transient
    override val instanceType: InstanceType = InstanceType.TYPE_B,
    override val data: TypeBData?
) : BaseInstance<TypeBData>()

@KSerializable
@SerialName("typec")
data class TypeCInstance(
    @Transient
    override val instanceType: InstanceType = InstanceType.TYPE_C,
    override val data: TypeCData?
) : BaseInstance<TypeCData>()

@KSerializable
@SerialName("unknown")
data class UnknownInstance(
    @Transient
    override val instanceType: InstanceType = InstanceType.UNKNOWN,
    @Transient
    override val data: UnknownData? = null
) : BaseInstance<UnknownData>()