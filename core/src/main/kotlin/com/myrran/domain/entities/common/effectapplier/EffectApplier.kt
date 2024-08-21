package com.myrran.domain.entities.common.effectapplier

import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.entities.common.vulnerable.DamageLocation
import com.myrran.domain.skills.created.effect.EffectSkill

interface EffectApplier {

    fun applyEffects(effectSkill: EffectSkill, target: Corporeal, location: DamageLocation)
}
