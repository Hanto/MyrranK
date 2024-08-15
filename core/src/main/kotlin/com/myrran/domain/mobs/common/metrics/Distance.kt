package com.myrran.domain.mobs.common.metrics

interface Distance
{
    fun toBox2DUnits(): Float
}

data class Pixel(

    private val value: Float

): Distance
{
    constructor(value: Int): this(value.toFloat())

    fun toFloat(): Float =

        value.toInt().toFloat()

    fun value(): Int =

        value.toInt()

    fun toMeters(): Meter =

        Meter(value * PIXEL_TO_METERS)

    override fun toBox2DUnits(): Float =

        toMeters().toFloat()

    operator fun div(int: Int): Pixel =

        Pixel(value / int)

    operator fun times(int: Float): Pixel =

        Pixel(value * int)
}

data class Meter(

    private val value: Float

): Distance
{
    fun toFloat(): Float =

        value

    fun toPixel(): Pixel =

        Pixel(value * METERS_TO_PIXEL)

    override fun toBox2DUnits(): Float =

        value

    operator fun plus(other: Meter) =

        Meter(value + other.value)

    operator fun div(int: Int): Meter =

        Meter(value / int)
}

private const val PIXEL_TO_METERS: Float = 0.032f
private const val METERS_TO_PIXEL: Float = 31.25f
