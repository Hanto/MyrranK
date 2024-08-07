package com.myrran.domain.misc

import java.util.Locale

fun Float.format(decimals: Int) =

    "%.${decimals}f".format(Locale.US,this)

inline fun <reified T, R>Any.letIf(function: (T) -> R?): R? =

    if (this is T) function.invoke(this as T) else null
