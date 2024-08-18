package com.myrran.domain.mobs.common.consumable

import com.myrran.domain.mobs.common.metrics.Second

interface Consumable {

    fun updateDuration(deltaTime: Float): IsConsumed
    fun willExpireIn(seconds: Second)

    data class IsConsumed(val isConsumed: Boolean)
}
