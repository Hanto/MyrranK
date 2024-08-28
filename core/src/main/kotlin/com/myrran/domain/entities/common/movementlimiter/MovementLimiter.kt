package com.myrran.domain.entities.common.movementlimiter

import com.badlogic.gdx.ai.steer.Limiter

interface MovementLimiter: Limiter {

    var slowModifier: Float
}
