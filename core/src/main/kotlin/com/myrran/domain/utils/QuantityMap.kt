package com.myrran.domain.utils

class QuantityMap<KEY>(

    private val map: MutableMap<KEY, Quantity> = mutableMapOf()

): MutableMap<KEY, Quantity> by map
{
    fun isAvailable(key: KEY): Boolean =

        map[key]?.let { it.available > 0 } ?: false

    fun add(key: KEY) {

        val quantity = map.computeIfAbsent(key) { Quantity(0, 0) }
        map[key] = Quantity(quantity.available + 1 , quantity.total + 1)
    }

    fun returnBack(key: KEY) {

        val quantity = map.computeIfAbsent(key) { Quantity(0,0) }
        map[key] = Quantity(quantity.available + 1, quantity.total)
    }

    fun borrow(key: KEY) {

        val quantity = map.computeIfAbsent(key) { Quantity(0, 0) }
        map[key] = Quantity(quantity.available - 1, quantity.total)
    }
}

data class Quantity(
    val available: Int,
    val total: Int
)
