package com.myrran.domain.entities.mob.spells.spell

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.Mob
import com.myrran.domain.entities.common.collisioner.Collisioner
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.entities.common.corporeal.Movable
import com.myrran.domain.entities.common.corporeal.Spatial
import com.myrran.domain.entities.common.steerable.Steerable
import com.myrran.domain.misc.metrics.PositionMeters

sealed interface Spell: Mob, Steerable, Spatial, Movable, Disposable,
    Consumable, Collisioner
{
    val caster: Entity
    fun addCollision(collisioned: Corporeal, pointOfCollision: PositionMeters)
}
