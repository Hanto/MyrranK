package com.myrran.domain.mob.metricunits

data class Meter(

    private val value: Float

): Distance
{
    fun toFloat(): Float =

        value

    fun toPixel(): Pixel =

        Pixel(value * 100)

    operator fun div(int: Int): Meter =

        Meter(value / int)
}
