package com.myrran.domain.misc

data class Quantity<VALUE>(

    val value: VALUE,
    val available: Int,
    val total: Int

) {
    companion object {

        @JvmStatic
        fun <VALUE>zero(item: VALUE) = Quantity(item, 0, 0)
    }

    fun isAvailable(): Boolean =
        available > 0

    fun increaseAvailable(): Quantity<VALUE> =
        Quantity(value = value, available = available + 1, total = total)

    fun decreaseAvailable(): Quantity<VALUE> =
        Quantity(value = value, available = available -1, total = total)

    fun increaseAvailableAndTotal(): Quantity<VALUE> =
        Quantity(value = value,available = available +1, total = total +1)

    fun <T>toQuantityOf(newItem: T): Quantity<T> =
        Quantity(newItem, available, total)
}
