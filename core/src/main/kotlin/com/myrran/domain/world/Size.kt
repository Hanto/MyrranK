package com.myrran.domain.world

data class Size<T: Distance>(

    val width: T,
    val height: T
)
