package com.example.benchmark

import androidx.annotation.RawRes
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.benchmarkable.gson.util.GsonFactory
import com.example.benchmarkable.model.contextual.ContextualInstance
import com.example.benchmarkable.model.generic.GenericListInstance
import com.example.benchmarkable.model.generic.InstanceType
import com.example.benchmarkable.model.simple.LinearInstance
import com.example.benchmarkable.model.simple.ListInstance
import com.example.benchmarkable.model.simple.NestingInstance
import com.google.gson.Gson
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
class GsonBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    private val gson by lazy { initGson() }

    @Test
    fun measureLinearInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.simplelinear)
        benchmarkRule.measureRepeated {
            val data = gson.fromJson(src, LinearInstance::class.java)
            runWithTimingDisabled {
                assertEquals("!!!", data?.string1)
            }
        }
    }

    @Test
    fun measureNestingInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.simplenesting)
        benchmarkRule.measureRepeated {
            val data = gson.fromJson(src, NestingInstance::class.java)
            runWithTimingDisabled {
                assertEquals("!!!", data?.string)
            }
        }
    }

    @Test
    fun measureSmallListInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.smalllistsimple)
        benchmarkRule.measureRepeated {
            val data = gson.fromJson(src, ListInstance::class.java)
            runWithTimingDisabled {
                assertEquals("!!!", data?.items?.get(0)?.testItem?.string1)
            }
        }
    }

    @Test
    fun measureLargeListInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.largelistsimple)
        benchmarkRule.measureRepeated {
            val data = gson.fromJson(src, ListInstance::class.java)
            runWithTimingDisabled {
                assertEquals("!!!", data?.items?.get(0)?.testItem?.string1)
            }
        }
    }

    @Test
    fun measureSmallGenericContainerInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.smalllistcustom)
        benchmarkRule.measureRepeated {
            val data = gson.fromJson(src, GenericListInstance::class.java)
            runWithTimingDisabled {
                assertEquals(
                    InstanceType.TYPE_A,
                    data?.items?.get(0)?.testItem?.instanceType
                )
            }
        }
    }

    @Test
    fun measureLargeGenericContainerInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.largelistcustom)
        benchmarkRule.measureRepeated {
            val data = gson.fromJson(src, GenericListInstance::class.java)
            runWithTimingDisabled {
                assertEquals(
                    InstanceType.TYPE_A,
                    data?.items?.get(0)?.testItem?.instanceType
                )
            }
        }
    }

    @Test
    fun measureContextualInstanceTest() {
        val src = getDataFromRawRes(BenchmarkableR.raw.contextual)
        benchmarkRule.measureRepeated {
            val data = gson.fromJson(src, ContextualInstance::class.java)
            runWithTimingDisabled {
                assertEquals(
                    3.14f,
                    data?.float1?.setScale(2, BigDecimal.ROUND_HALF_UP)?.toFloat() ?: 0f,
                    0.001f
                )
            }
        }
    }

    private fun initGson(): Gson {
        return benchmarkRule.scope.runWithTimingDisabled {
            GsonFactory.provideGsonInstance()
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