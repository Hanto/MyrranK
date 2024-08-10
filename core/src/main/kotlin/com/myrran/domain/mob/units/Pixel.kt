package com.myrran.domain.mob.units

data class Pixel(

    private val value: Float

): Distance
{
    fun toFloat(): Float =

        value

    fun value(): Int =

        value.toInt()

    fun toMeters(): Meter =

        Meter(value * 0.01f)

    operator fun div(int: Int): Pixel =

        Pixel(value / int)
}
