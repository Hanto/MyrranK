package com.myrran.domain.misc

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.concurrent.TimeUnit

class Clock
{
    fun currentTime(): Time = Time()
    fun elapsedTimeSince(start: Time) = ElapsedTime.since(start)
}

data class ElapsedTime(private val value: Long)
{
    companion object
    {
        @JvmStatic
        fun since(start: Time): ElapsedTime =
            ElapsedTime(Time().toMillis() - start.toMillis())

        fun of(units: Long, timeUnit: TimeUnit): ElapsedTime =
            ElapsedTime(timeUnit.toMillis(units))
    }

    fun toMillis(): Long = value

    operator fun plus(other: ElapsedTime): ElapsedTime =

        ElapsedTime(toMillis() + toMillis())

    operator fun compareTo(other: ElapsedTime): Int =

        value.compareTo(other.value)
}

data class Time(val value: Long = System.currentTimeMillis())
{
    fun toMillis(): Long = value
    fun toDate(): Date = Date(value)

    fun toLocalDateTime(): LocalDateTime =

        Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime()

    fun toTimeUnit(unit: TimeUnit): Long =

        unit.convert(value, TimeUnit.MILLISECONDS)

    operator fun plus(other: ElapsedTime): Time =

        Time(value + other.toMillis())

    operator fun minus(other: Time): ElapsedTime =

        ElapsedTime(toMillis() - other.toMillis())
}

