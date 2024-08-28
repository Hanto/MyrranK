package com.myrran.domain.entities.common.consumable

import com.myrran.domain.entities.common.consumable.Consumable.IsConsumed
import com.myrran.domain.misc.metrics.time.Second
import com.myrran.domain.misc.metrics.time.Time

class ConsumableComponent(

    private var currentDuration: Time = Second(0),
    private var maximumDuration: Time = Second(0)

): Consumable
{
    override fun willExpireIn(time: Time) {

        currentDuration = currentDuration.toZero()
        maximumDuration = time
    }

    override fun resetDuration() {

        currentDuration = currentDuration.toZero() }

    override fun updateDuration(elapsedTime: Time): IsConsumed {

        currentDuration += elapsedTime
        return IsConsumed(currentDuration >= maximumDuration)
    }

    override fun currentDuration(): Time =

        currentDuration

    override fun remainingDuration(): Time =

        maximumDuration - currentDuration

    override fun hasExpired(): Boolean =

        maximumDuration - currentDuration <= currentDuration.toZero()

    override fun hasStarted(): Boolean =

        currentDuration == currentDuration.toZero()
}
