package com.myrran.domain.world

data class Radian(

    private val value: Float

): Angle
{
    fun toFloat(): Float =

        value
}
