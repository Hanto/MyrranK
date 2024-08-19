package com.myrran.domain.misc.metrics

interface Angle

data class Degree(

    private val value: Float

): Angle
{
    fun toFloat(): Float =

        value % 360

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
}

data class Radian(

    private val value: Float

): Angle
{
    fun toFloat(): Float =

        value % 6.283185f

    fun toDegrees(): Degree =

        Degree(value * 57.29578f)
}
