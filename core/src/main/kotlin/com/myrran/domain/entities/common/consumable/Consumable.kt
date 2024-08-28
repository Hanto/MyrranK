package com.myrran.domain.entities.common.consumable

import com.myrran.domain.misc.metrics.time.Time

interface Consumable {

    fun resetDuration()
    fun currentDuration(): Time
    fun remainingDuration(): Time
    fun updateDuration(elapsedTime: Time): IsConsumed
    fun willExpireIn(time: Time)
    fun hasStarted(): Boolean
    fun hasExpired(): Boolean

    data class IsConsumed(val isConsumed: Boolean)
}
