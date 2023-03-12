package com.example.benchmark

import androidx.annotation.RawRes
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.benchmarkable.kotlinx.util.KotlinxJsonFactory
import com.example.benchmarkable.model.contextual.ContextualInstance
import com.example.benchmarkable.model.generic.CustomListInstance
import com.example.benchmarkable.model.generic.GenericListInstance
import com.example.benchmarkable.model.generic.InstanceType
import com.example.benchmarkable.model.payload.ExampleResponse
import com.example.benchmarkable.model.payload.ResponsePayload
import com.example.benchmarkable.model.simple.LinearInstance
import com.example.benchmarkable.model.simple.ListInstance
import com.example.benchmarkable.model.simple.NestingInstance
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal
import com.example.benchmarkable.R as BenchmarkableR

/**
 * Benchmark, which will execute on an Android device.
 *
 * The body of [BenchmarkRule.measureRepeated] is measured in a loop, and Studio will
 * output the result. Modify your code to see how it affects performance.
 */
@RunWith(AndroidJUnit4::class)
class KotlinxBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    private val json by lazy { initKotlinxJson() }

    @Test
    fun measureLinearInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.simplelinear)
        benchmarkRule.measureRepeated {
            val data = json.decodeFromString(LinearInstance.serializer(), src)
            runWithTimingDisabled {
                assertEquals("!!!", data.string1)
            }
        }
    }

    @Test
    fun measureNestingInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.simplenesting)
        benchmarkRule.measureRepeated {
            val data = json.decodeFromString(NestingInstance.serializer(), src)
            runWithTimingDisabled {
                assertEquals("!!!", data.string)
            }
        }
    }

    @Test
    fun measureSmallListInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.smalllistsimple)
        benchmarkRule.measureRepeated {
            val data = json.decodeFromString(ListInstance.serializer(), src)
            runWithTimingDisabled {
                assertEquals("!!!", data.items[0].testItem.string1)
            }
        }
    }

    @Test
    fun measureLargeListInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.largelistsimple)
        benchmarkRule.measureRepeated {
            val data = json.decodeFromString(ListInstance.serializer(), src)
            runWithTimingDisabled {
                assertEquals("!!!", data.items[0].testItem.string1)
            }
        }
    }

    @Test
    fun measureSmallGenericContainerInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.smalllistcustom)
        benchmarkRule.measureRepeated {
            val data = json.decodeFromString(GenericListInstance.serializer(), src)
            runWithTimingDisabled {
                assertEquals(
                    InstanceType.TYPE_A,
                    data.items[0].testItem.instanceType
                )
            }
        }
    }

    @Test
    fun measureLargeGenericContainerInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.largelistcustom)
        benchmarkRule.measureRepeated {
            val data = json.decodeFromString(GenericListInstance.serializer(), src)
            runWithTimingDisabled {
                assertEquals(
                    InstanceType.TYPE_A,
                    data.items[0].testItem.instanceType
                )
            }
        }
    }

    @Test
    fun measureContextualInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.contextual)
        benchmarkRule.measureRepeated {
            val data = json.decodeFromString(ContextualInstance.serializer(), src)
            runWithTimingDisabled {
                assertEquals(
                    3.14f,
                    data.float1.setScale(2, BigDecimal.ROUND_HALF_UP).toFloat(),
                    0.001f
                )
            }
        }
    }

    @Test
    fun testCustomContainerInstance() {
        val src = getDataFromRawRes(BenchmarkableR.raw.smalllistcustom)
        val data = json.decodeFromString(CustomListInstance.serializer(), src)
        assertEquals(
            InstanceType.TYPE_A,
            data.items[0].testItem.instanceType
        )
    }

    @Test
    fun testPayloadInstanceWithCustomSerializer() {
        val src = getDataFromRawRes(BenchmarkableR.raw.responsewithpayload)
        val data = json.decodeFromString(
            ResponsePayload.serializer(ExampleResponse.serializer()),
            src
        )
        assertEquals(
            "Some data from backend",
            data.payload.someData
        )
    }

    private fun initKotlinxJson(): Json {
        return benchmarkRule.scope.runWithTimingDisabled {
            KotlinxJsonFactory.provideKotlinxJsonInstance()
        }
    }

    private fun getDataFromRawRes(@RawRes resId: Int): String {
        return benchmarkRule.scope.runWithTimingDisabled {
            InstrumentationRegistry.getInstrumentation()
                .context.resources.openRawResource(resId)
                .bufferedReader().use { it.readText() }
        }
    }
}