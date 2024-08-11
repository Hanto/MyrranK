package com.myrran.domain.mob

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.math.Vector2
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mob.metrics.PositionMeters

interface Mob: Steerable<Vector2>, Identifiable<MobId> {

    fun setPosition(position: PositionMeters)
    fun saveLastPosition()
}
