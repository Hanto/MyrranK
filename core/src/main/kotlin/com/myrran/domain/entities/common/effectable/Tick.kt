package com.myrran.domain.entities.common.effectable

import com.myrran.domain.misc.metrics.Second

data class Tick
(
    private val tickTime: Second,
)
{
    companion object {

        val SECONDS_FOR_A_TICK = Second(1)
    }

    fun toTick(): Int =

        if (tickTime.isZero()) 0
        else (tickTime / SECONDS_FOR_A_TICK + 1 ).toInt()

    operator fun plus(time: Second) =

        Tick(tickTime + time)
}
