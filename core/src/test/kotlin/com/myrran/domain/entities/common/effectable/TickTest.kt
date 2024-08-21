package com.myrran.domain.entities.common.effectable

import com.myrran.domain.misc.metrics.Second
import org.junit.jupiter.api.Test

class TickTest {

    @Test
    fun test() {

        var underTest = Tick(Second(0))
        println(underTest.toTick())

        underTest = underTest.plus(Second(0.4f))
        println(underTest.toTick())

        underTest = underTest.plus(Second(0.4f))
        println(underTest.toTick())

        underTest = underTest.plus(Second(0.4f))
        println(underTest.toTick())
    }
}
