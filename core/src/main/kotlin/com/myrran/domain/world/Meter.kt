package com.myrran.domain.world

data class Meter(

    val value: Float
)
{
    fun toPixel(): Pixel =

        Pixel(value * 100)
}
