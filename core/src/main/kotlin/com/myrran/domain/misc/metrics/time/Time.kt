package com.myrran.domain.misc.metrics.time


interface Time
{
    companion object {

        const val SECONDS_FOR_A_TICK = 1f
    }

    operator fun plus(other: Time): Time
    operator fun minus(other: Time): Time
    operator fun compareTo(other: Time): Int
    fun min(other: Time): Time
    fun max(other: Time): Time
    fun isZero(): Boolean
    fun toSeconds(): Second
    fun toTicks(): Tick
    fun toBox2DUnits(): Float
    fun toZero(): Time
}
