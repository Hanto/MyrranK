package com.myrran.domain.misc.metrics.time

data class Second(

    private val value: Float

): Time
{
    constructor(value: Int): this(value.toFloat())

    companion object  {

        fun fromBox2DUnits(unit: Float) =

            Second(unit)
    }

    // TO:
    //--------------------------------------------------------------------------------------------------------

    override fun toTicks(): Tick =

        Tick(value / Time.SECONDS_FOR_A_TICK)

    override fun toSeconds(): Second =

        this

    override fun toBox2DUnits(): Float =

        value

    override fun toZero(): Time =

        Second(0f)

    // OPERATIONS:
    //--------------------------------------------------------------------------------------------------------

    override operator fun plus(other: Time): Time =

        this + other.toSeconds()

    operator fun plus(other: Second): Second =

        Second(value + other.value)

    override fun minus(other: Time): Time =

        this - other.toSeconds()

    operator fun minus(other: Second): Second =

        Second(value - other.value)

    override operator fun compareTo(other: Time): Int =

        compareTo(other.toSeconds())

    operator fun compareTo(other: Second): Int =

        value.compareTo(other.value)

    operator fun div(other: Second): Float =

        value / other.value

    override fun isZero(): Boolean =

        value == 0f

    override fun min(other: Time): Time =

        Second( value.coerceAtLeast(other.toSeconds().value) )

    override fun max(other: Time): Time =

        Second( value.coerceAtMost(other.toSeconds().value) )
}
