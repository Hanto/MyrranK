package com.myrran.domain.mob

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.math.Vector2
import com.myrran.domain.mob.metrics.PositionMeters

interface Movable: Steerable<Vector2> {

    fun setLinearVelocity(direction: Vector2, value: Float)
    fun setPosition(position: PositionMeters)
    fun saveLastPosition()
    fun getLastPosition(): Vector2
}
