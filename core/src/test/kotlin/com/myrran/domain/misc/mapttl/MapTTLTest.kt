package com.myrran.domain.misc.mapttl

import com.myrran.domain.misc.ElapsedTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

class MapTTLTest {

    private val underTest = MapTTL<String, String>()

    @Test
    fun `when Get should refresh the TimeToLive`()
    {
        underTest["KEY", ElapsedTime.of(150, TimeUnit.MILLISECONDS)] = "VALUE"

        Thread.sleep(100)

        assertThat(underTest["KEY"]).isNotNull()

        Thread.sleep(100)

        assertThat(underTest["KEY"]).isNotNull()

        Thread.sleep(160)

        assertThat(underTest["KEY"]).isNull()
    }

    @Test
    fun `when Put should refresh the TimeToLive`()
    {
        underTest["KEY", ElapsedTime.of(150, TimeUnit.MILLISECONDS)] = "VALUE"

        Thread.sleep(100)

        underTest["KEY", ElapsedTime.of(150, TimeUnit.MILLISECONDS)] = "VALUE"

        Thread.sleep(100)

        assertThat(underTest["KEY"]).isNotNull()

        Thread.sleep(100)

        assertThat(underTest["KEY"]).isNotNull()

        Thread.sleep(160)

        assertThat(underTest["KEY"]).isNull()
    }
}
