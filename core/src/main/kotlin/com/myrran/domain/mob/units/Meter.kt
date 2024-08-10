package com.myrran.domain.mob.units

data class Meter(

    private val value: Float

): Distance
{
    fun toFloat(): Float =

        value

    fun toPixel(): Pixel =

        Pixel(value * 100)
}
