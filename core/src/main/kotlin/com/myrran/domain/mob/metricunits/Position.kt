package com.myrran.domain.mob.metricunits

data class Position<T: Distance>(

    val x: T,
    val y: T
)
