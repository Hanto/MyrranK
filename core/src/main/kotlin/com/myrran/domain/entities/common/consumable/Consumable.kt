package com.myrran.domain.entities.common.consumable

import com.myrran.domain.misc.metrics.Second

interface Consumable {

    fun updateDuration(deltaTime: Float): IsConsumed
    fun willExpireIn(seconds: Second)

    data class IsConsumed(val isConsumed: Boolean)
}
