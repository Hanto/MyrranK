package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId

interface Effect {

    val casterId: EntityId
    fun tickEffect(entity: Entity)

}
