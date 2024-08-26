package com.myrran.domain.misc.metrics.time

data class
Tick(

    private val value: Float

): Time
{
    constructor(value: Int): this(value.toFloat())

    companion object {

        fun fromBox2DUnits(unit: Float) =

            Tick(unit / Time.SECONDS_FOR_A_TICK)

        fun fromNumberOfTicks(numberOfTicks: Int) =

            Tick(numberOfTicks * Time.SECONDS_FOR_A_TICK)
    }

    // TO:
    //--------------------------------------------------------------------------------------------------------

    override fun toTicks(): Tick =

        this

    override fun toSeconds(): Second =

        Second(value * Time.SECONDS_FOR_A_TICK)

    override fun toBox2DUnits(): Float =

        value * Time.SECONDS_FOR_A_TICK

    override fun toZero(): Time =

        Tick(0)

    fun toTickNumber(): Int =

        (value / Time.SECONDS_FOR_A_TICK + 1 ).toInt()

    // OPERATIONS:
    //--------------------------------------------------------------------------------------------------------

    override operator fun plus(other: Time): Time =

        this + other.toTicks()

    operator fun plus(other: Tick): Tick =

        Tick(value + other.value)

    override fun minus(other: Time): Time =

        this - other.toTicks()

    operator fun minus(other: Tick): Tick =

        Tick(value - other.value)

    override operator fun compareTo(other: Time): Int =

        compareTo(other.toTicks())

    fun compareTo(other: Tick): Int =

        value.compareTo(other.value)

    override fun isZero(): Boolean =

        value == 0f

    override fun min(other: Time): Time =

        Tick( value.coerceAtLeast(other.toTicks().value) )

    override fun max(other: Time): Time =

        Tick( value.coerceAtMost(other.toTicks().value) )
}
