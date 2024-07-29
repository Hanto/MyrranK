package com.myrran.domain.utils.mapttl

import com.myrran.domain.utils.Clock
import com.myrran.domain.utils.ElapsedTime
import com.myrran.domain.utils.Time
import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit

data class ExpiringKey<KEY>(

    val key: KEY,
    private val clock: Clock = Clock(),
    private var creationTime: Time = clock.currentTime(),
    private var ttl: ElapsedTime

): Delayed {

    fun renew() {

        creationTime = clock.currentTime()
    }

    fun expire() {

        ttl = ElapsedTime.of(0, TimeUnit.MILLISECONDS)
    }

    override fun compareTo(other: Delayed): Int =

        this.getDelay(TimeUnit.MILLISECONDS).compareTo(other.getDelay(TimeUnit.MILLISECONDS))

    override fun getDelay(unit: TimeUnit): Long =

        ( creationTime + ttl - clock.currentTime() ).toMillis()
}
