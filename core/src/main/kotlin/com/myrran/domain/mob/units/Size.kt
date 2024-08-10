package com.myrran.domain.mob.units

data class Size<T: Distance>(

    val width: T,
    val height: T
)
