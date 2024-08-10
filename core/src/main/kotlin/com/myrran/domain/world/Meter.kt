package com.myrran.domain.world

data class Meter(

    private val value: Float

): Distance
{
    fun toFloat(): Float =

        value

    fun toPixel(): Pixel =

        Pixel(value * 100)
}
