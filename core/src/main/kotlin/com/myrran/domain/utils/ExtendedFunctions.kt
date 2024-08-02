package com.myrran.domain.utils

import java.util.Locale

fun Float.format(decimals: Int) =

    "%.${decimals}f".format(Locale.US,this)
