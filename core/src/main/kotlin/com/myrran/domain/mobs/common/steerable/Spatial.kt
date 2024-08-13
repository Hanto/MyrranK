package com.myrran.domain.mobs.common.steerable

import com.badlogic.gdx.ai.utils.Location
import com.badlogic.gdx.math.Vector2

interface Spatial: Location<Vector2> {

    fun getInterpolatedPosition(fractionOfTimestep: Float): Vector2
    fun setPosition(position: Vector2)
    fun saveLastPosition()
}
