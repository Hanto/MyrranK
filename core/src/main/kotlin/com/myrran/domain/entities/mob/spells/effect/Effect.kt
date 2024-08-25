package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.mob.spells.effect.stackable.Stackable

interface Effect: Consumable, Stackable {

    val casterId: EntityId
    fun tickEffect(entity: Entity)

}
