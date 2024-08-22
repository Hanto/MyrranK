package com.myrran.domain.misc.metrics

interface Angle

class Degree(

    val value: Float

): Angle
{
    fun toFloat(): Float =

        value

    fun normalize(): Degree =

        Degree( (value + 360) % 360 )

    fun toRadians(): Radian =

        Radian(0.01745330f * value)

    operator fun times(number: Number) =

        Degree(value * number.toFloat())

    operator fun div(number: Number) =

        Degree(value / number.toFloat())

    operator fun minus(number: Number) =

        Degree(value - number.toFloat())

    operator fun minus(other: Degree) =

        Degree(value - other.value)

    operator fun compareTo(other: Degree) =

        value.compareTo(other.value)
}

data class Radian(

    private val value: Float

): Angle
{
    fun toFloat(): Float =

        value

    fun normalize(): Radian =

        Radian( value % 6.283185f)

    fun toDegrees(): Degree =

        Degree(value * 57.29578f)
}
