package com.myrran.domain.entities.common.vulnerable

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.misc.metrics.PositionMeters

data class DamageLocation(

    val position: PositionMeters,
    val direction: Vector2
)
