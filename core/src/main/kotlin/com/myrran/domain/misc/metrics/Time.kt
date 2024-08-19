package com.myrran.domain.misc.metrics

interface Time
{
    fun toBox2DUnits(): Float
}

data class Second(

    private val value: Float

): Time
{
    constructor(value: Int): this(value.toFloat())

    companion object  {

        fun fromBox2DUnits(unit: Float) =

            Second(unit)
    }

    override fun toBox2DUnits(): Float =

        value

    operator fun plus(other: Second): Second =

        Second(value + other.value)

    operator fun div(other: Second): Float =

        value / other.value

    operator fun compareTo(other: Second): Int =

        value.compareTo(other.value)

    fun isZero(): Boolean =

        value == 0f
}
