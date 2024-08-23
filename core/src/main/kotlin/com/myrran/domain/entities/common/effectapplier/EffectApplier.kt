package com.myrran.domain.entities.common.effectapplier

import com.myrran.domain.entities.common.collisioner.Collision
import com.myrran.domain.skills.created.effect.EffectSkill

interface EffectApplier {

    fun applyEffects(effectSkill: EffectSkill, collision: Collision)
}
