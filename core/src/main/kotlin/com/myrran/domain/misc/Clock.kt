package com.myrran.domain.misc

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.concurrent.TimeUnit

class Clock
{
    fun currentTime(): PointInTime = PointInTime(System.currentTimeMillis())
    fun elapsedTimeSince(start: PointInTime) = ElapsedTime.since(start)
}

data class ElapsedTime(private val value: Long)
{
    companion object
    {
        @JvmStatic
        fun since(start: PointInTime): ElapsedTime =
            ElapsedTime(PointInTime().toMillis() - start.toMillis())

        fun of(units: Long, timeUnit: TimeUnit): ElapsedTime =
            ElapsedTime(timeUnit.toMillis(units))
    }

    fun toMillis(): Long = value

    operator fun plus(other: ElapsedTime): ElapsedTime =

        ElapsedTime(toMillis() + toMillis())

    operator fun compareTo(other: ElapsedTime): Int =

        value.compareTo(other.value)
}

data class PointInTime(val value: Long = System.currentTimeMillis())
{
    fun toMillis(): Long = value
    fun toDate(): Date = Date(value)

    fun toLocalDateTime(): LocalDateTime =

        Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime()

    fun toTimeUnit(unit: TimeUnit): Long =

        unit.convert(value, TimeUnit.MILLISECONDS)

    operator fun plus(other: ElapsedTime): PointInTime =

        PointInTime(value + other.toMillis())

    operator fun minus(other: PointInTime): ElapsedTime =

        ElapsedTime(toMillis() - other.toMillis())
}

