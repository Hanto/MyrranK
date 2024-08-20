package com.myrran.domain.entities.common.consumable

import com.myrran.domain.entities.common.consumable.Consumable.IsConsumed
import com.myrran.domain.misc.metrics.Second

class ConsumableComponent(

    private var maximumDuration: Second = Second(1)

): Consumable
{
    private var currentDuration: Second = Second(0f)

    override fun willExpireIn(seconds: Second) {

        currentDuration = Second(0)
        maximumDuration = seconds
    }

    override fun updateDuration(deltaTime: Float): IsConsumed {

        currentDuration += Second.fromBox2DUnits(deltaTime)
        return IsConsumed(currentDuration >= maximumDuration)
    }
}
