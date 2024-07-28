package com.myrran.utils

class QuantityMap<T>(

    private val map: MutableMap<T, Quantity> = mutableMapOf()

): MutableMap<T, Quantity> by map
{
    fun isAvailable(key: T): Boolean =

        map[key]?.let { it.available > 0 } ?: false

    fun add(key: T) {

        val quantity = map.computeIfAbsent(key) { Quantity(0, 0) }
        map[key] = Quantity(quantity.available + 1 , quantity.total + 1)
    }

    fun returnBack(key: T) {

        val quantity = map.computeIfAbsent(key) { Quantity(0,0) }
        map[key] = Quantity(quantity.available + 1, quantity.total)
    }

    fun borrow(key: T) {

        val quantity = map.computeIfAbsent(key) { Quantity(0, 0) }
        map[key] = Quantity(quantity.available - 1, quantity.total)
    }
}

data class Quantity(
    val available: Int,
    val total: Int
)
