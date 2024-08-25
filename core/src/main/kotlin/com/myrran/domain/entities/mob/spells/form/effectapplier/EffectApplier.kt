package com.myrran.domain.entities.mob.spells.form.effectapplier

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.collisioner.Collision
import com.myrran.domain.skills.created.effect.EffectSkill

interface EffectApplier {

    fun applyEffects(caster: Entity, effectSkill: EffectSkill, collision: Collision)
}
