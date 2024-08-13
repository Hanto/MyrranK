package com.myrran.domain.mob.steerable

import com.badlogic.gdx.ai.utils.Location
import com.badlogic.gdx.math.Vector2

interface Spatial: Location<Vector2> {

    fun setPosition(position: Vector2)
    fun saveLastPosition()
    fun getLastPosition(): Vector2
}
