package com.myrran.domain.entities.common.effectable

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.mob.spells.effect.Effect
import com.myrran.domain.misc.metrics.time.Second

interface Effectable {

    fun retrieveEffects(): List<Effect>
    fun addEffect(effect: Effect)
    fun updateEffects(effectable: Entity, deltaTime: Second)
}
