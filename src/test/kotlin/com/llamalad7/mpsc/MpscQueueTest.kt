package com.llamalad7.mpsc

import org.jetbrains.kotlinx.lincheck.annotations.Operation
import org.jetbrains.kotlinx.lincheck.check
import org.jetbrains.kotlinx.lincheck.strategy.managed.modelchecking.ModelCheckingOptions
import org.jetbrains.kotlinx.lincheck.strategy.stress.StressOptions
import kotlin.test.Test

class MpscQueueTest {
    private val queue = MpscQueue<Int>()

    @Operation
    fun push(value: Int) = queue.push(value)

    @Operation(nonParallelGroup = "consumer")
    fun pop() = queue.pop()

    @Test
    fun stressTest() = StressOptions().check(this::class)

    @Test
    fun modelCheckingTest() = ModelCheckingOptions().check(this::class)
}