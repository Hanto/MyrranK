package com.myrran.domain.entities.common.vulnerable

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.misc.metrics.PositionMeters

sealed interface DamageLocation {

    data class Location(

        val position: PositionMeters,
        val direction: Vector2

    ): DamageLocation

    data object NoLocation: DamageLocation
}
