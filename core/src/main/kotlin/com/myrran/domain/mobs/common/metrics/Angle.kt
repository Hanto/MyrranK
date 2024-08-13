package com.myrran.domain.mobs.common.metrics

interface Angle

data class Degree(

    private val value: Float

): Angle
{
    fun toFloat(): Float =

        value % 360

    fun toRadians(): Radian =

        Radian(0.01745330f * value)
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
