package com.myrran.domain.entities.mob.spells.form.effectapplier

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.collisioner.Collision
import com.myrran.domain.entities.common.effectable.Effectable
import com.myrran.domain.entities.mob.spells.effect.EffectFactory
import com.myrran.domain.skills.created.effect.EffectSkill

class EffectApplierComponent(

    private val effectFactory: EffectFactory

): EffectApplier {

    // EFFECT APPLYING
    //--------------------------------------------------------------------------------------------------------

    override fun applyEffects(caster: Entity, effectSkill: EffectSkill, collision: Collision) {

        if (collision.corporeal is Effectable) {

            val effect = effectFactory.createEffect(effectSkill, caster, collision.location)
            collision.corporeal.addEffect(effect)
        }
    }
}
