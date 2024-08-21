package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity

interface Effect {

    fun tickEffect(entity: Entity, deltaTime: Float)

}
