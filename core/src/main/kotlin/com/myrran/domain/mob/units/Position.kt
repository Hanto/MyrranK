package com.myrran.domain.mob.units

data class Position<T: Distance>(

    val x: T,
    val y: T
)
