package com.myrran.domain.mob.metricunits

data class Size<T: Distance>(

    val width: T,
    val height: T
)
