package com.myrran.domain.mob.metricunits

data class Radian(

    private val value: Float

): Angle
{
    fun toFloat(): Float =

        value
}
