package com.myrran.domain.mob.units

data class Radian(

    private val value: Float

): Angle
{
    fun toFloat(): Float =

        value
}
