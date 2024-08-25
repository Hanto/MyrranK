package com.myrran.domain.entities.common.collisioner

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.misc.metrics.PositionMeters

data class Collision(
    val corporeal: Corporeal,
    val pointOfCollision: PositionMeters,
    val direction: Vector2

): Location

sealed interface Location
data object NoLocation: Location
