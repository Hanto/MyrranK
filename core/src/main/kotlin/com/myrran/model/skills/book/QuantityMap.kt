package com.myrran.model.skills.book

class QuantityMap<T>
{
    val map = mutableMapOf<T, Int>()

    fun isAvailable(key: T): Boolean =

        map[key]?.let { it > 0 } ?: false

    fun add(key: T) {

        val actualQuantity = map.computeIfAbsent(key) { 0 }
        map[key] = actualQuantity + 1
    }

    fun borrow(key: T) {

        val actualQuantity = map.computeIfAbsent(key) { 0 }
        map[key] = actualQuantity - 1
    }
}
