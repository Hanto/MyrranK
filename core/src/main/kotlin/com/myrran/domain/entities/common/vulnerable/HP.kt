package com.myrran.domain.entities.common.vulnerable

data class HP(

    val value: Float
)
{
    operator fun plus(other: HP) =

        HP(value + other.value)

    operator fun minus(other: HP) =

        HP(value - other.value)

    operator fun div(other: HP): Float =

        value / other.value

    operator fun compareTo(other: HP): Int =

        value.compareTo(other.value)

    fun atMin(min: HP) =

        HP(value.coerceAtLeast(min.value))

    fun atMax(max: HP) =

        HP(value.coerceAtMost(max.value))
}
