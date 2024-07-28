package com.myrran.utils

class QuantityMap<T>(

    private val map: MutableMap<T, Int> = mutableMapOf()

): MutableMap<T, Int> by map
{
    fun isAvailable(key: T): Boolean =

        map[key]?.let { it > 0 } ?: false

    fun returnBack(key: T) {

        val actualQuantity = map.computeIfAbsent(key) { 0 }
        map[key] = actualQuantity + 1
    }

    fun borrow(key: T) {

        val actualQuantity = map.computeIfAbsent(key) { 0 }
        map[key] = actualQuantity - 1
    }
}
