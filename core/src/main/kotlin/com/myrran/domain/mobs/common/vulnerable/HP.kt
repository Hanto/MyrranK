package com.myrran.domain.mobs.common.vulnerable

data class HP(

    val value: Float
)
{
    operator fun plus(other: HP) =
        HP(value + other.value)

    operator fun minus(other: HP) =
        HP(value - other.value)

    fun atMin(min: HP) =
        HP(value.coerceAtLeast(min.value))

    fun atMax(max: HP) =
        HP(value.coerceAtMost(max.value))
}
