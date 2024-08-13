package com.myrran.domain.mobs.common.consumable

interface Consumable {

    fun updateDuration(deltaTime: Float): IsConsumed

    data class IsConsumed(val isConsumed: Boolean)
}
