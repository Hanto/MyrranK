package com.myrran.domain.entities.common.effectable

import com.myrran.domain.misc.metrics.Second
import com.myrran.domain.misc.metrics.Tick
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TickTest {

    @Test
    fun test() {

        var underTest = Tick(0)
        assertThat(underTest.toTickNumber()).isZero()

        underTest = underTest.plus(Second(0.4f).toTicks())
        assertThat(underTest.toTickNumber()).isOne()

        underTest = underTest.plus(Second(0.4f).toTicks())
        assertThat(underTest.toTickNumber()).isOne()

        underTest = underTest.plus(Second(0.4f).toTicks())
        assertThat(underTest.toTickNumber()).isEqualTo(2)
    }
}
