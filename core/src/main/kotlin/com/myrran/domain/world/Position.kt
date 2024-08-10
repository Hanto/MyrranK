package com.myrran.domain.world

data class Position<T: Distance>(

    val x: T,
    val y: T
)
