package com.myrran.utils

class QuantityMap2<KEY, VALUE>(

    private val map: MutableMap<KEY, ValueQuantity<VALUE>>

): MutableMap<KEY, ValueQuantity<VALUE>> by map
{
    fun isAvailable(key: KEY): Boolean =

        map[key]?.let { it.quantity.available > 0 } ?: false

    fun add(key: KEY, value: VALUE) {

        val result = map.computeIfAbsent(key) { ValueQuantity(value, Quantity(0, 0)) }
        result.quantity = Quantity(result.quantity.available + 1, result.quantity.total + 1)
    }

    fun returnBack(key: KEY) {

        val result = map[key]!!
        result.quantity = Quantity(result.quantity.available + 1, result.quantity.total)
    }

    fun borrow(key: KEY) {

        if (!isAvailable(key))
            throw RuntimeException("the following key: [$key] is not available")

        val result = map[key]!!
        result.quantity = Quantity(result.quantity.available - 1, result.quantity.total)
    }
}

data class ValueQuantity<VALUE>(
    val value: VALUE,
    var quantity: Quantity
)
