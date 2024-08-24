package com.myrran.domain.misc.metrics


interface Time
{
    companion object {

        const val SECONDS_FOR_A_TICK = 1f
    }

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

    fun toTicks(): Tick =

        Tick(value / Time.SECONDS_FOR_A_TICK)

    override fun toBox2DUnits(): Float =

        value

    operator fun plus(other: Second): Second =

        Second(value + other.value)

    operator fun minus(other: Second): Second =

        Second(value - other.value)

    operator fun div(other: Second): Float =

        value / other.value

    operator fun compareTo(other: Second): Int =

        value.compareTo(other.value)

    fun isZero(): Boolean =

        value == 0f
}

data class Tick(

    private val value: Float

): Time
{
    constructor(value: Int): this(value.toFloat())

    fun toSeconds(): Second =

        Second(value * Time.SECONDS_FOR_A_TICK)

    fun toTickNumber(): Int =

        if (value == 0f) 0
        else (value / Time.SECONDS_FOR_A_TICK + 1 ).toInt()

    override fun toBox2DUnits(): Float =

        value * Time.SECONDS_FOR_A_TICK

    operator fun plus(other: Tick): Tick =

        Tick(value + other.value)
}
