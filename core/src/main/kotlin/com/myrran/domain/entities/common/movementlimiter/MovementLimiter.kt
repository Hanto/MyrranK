package com.myrran.domain.entities.common.movementlimiter

import com.badlogic.gdx.ai.steer.Limiter
import com.myrran.domain.entities.common.EntityId

interface MovementLimiter: Limiter {

    fun addSlowModifier(effectId: EntityId, float: Float)
    fun removeSlowModifier(effectId: EntityId)
}
