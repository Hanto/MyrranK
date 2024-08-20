package com.myrran.domain.entities.common.proximityaware

import com.badlogic.gdx.ai.steer.Proximity
import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.math.Vector2

interface ProximityAware: Proximity<Vector2> {

    fun addNeighbor(steerable: Steerable<Vector2>)
    fun removeNeighbor(steerable: Steerable<Vector2>)
}
