package com.myrran.domain.entities.common.consumable

import com.myrran.domain.misc.metrics.time.Time

interface Consumable {

    fun currentDuration(): Time
    fun remainingDuration(): Time
    fun updateDuration(time: Time): IsConsumed
    fun willExpireIn(time: Time)

    data class IsConsumed(val isConsumed: Boolean)
}
