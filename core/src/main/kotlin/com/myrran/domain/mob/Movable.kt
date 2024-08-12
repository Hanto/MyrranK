package com.myrran.domain.mob

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.ai.utils.Location
import com.badlogic.gdx.math.Vector2

interface Movable: Steerable<Vector2> {

    val location: Location<Vector2>
    fun setLinearVelocity(direction: Vector2, value: Float)
    fun setPosition(position: Vector2)
    fun saveLastPosition()
    fun getLastPosition(): Vector2
}
