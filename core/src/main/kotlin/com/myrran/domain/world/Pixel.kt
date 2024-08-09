package com.myrran.domain.world

data class Pixel(

    private val value: Float
)
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
