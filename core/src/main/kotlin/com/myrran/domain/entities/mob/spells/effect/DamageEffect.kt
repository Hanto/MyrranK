package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.effectable.Tick
import com.myrran.domain.misc.metrics.Second
import com.myrran.domain.skills.created.effect.EffectSkill

class DamageEffect(

    private val effectSkill: EffectSkill,

): Effect
{
    private var tick: Tick = Tick(Second(0))

    override fun tickEffect(entity: Entity, deltaTime: Float) {

    }
}
