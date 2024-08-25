package com.myrran.domain.entities.mob.spells.form.effectapplier

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.collisioner.Collision
import com.myrran.domain.entities.common.effectable.Effectable
import com.myrran.domain.entities.common.vulnerable.Damage
import com.myrran.domain.entities.common.vulnerable.DamageType
import com.myrran.domain.entities.common.vulnerable.HP
import com.myrran.domain.entities.common.vulnerable.Vulnerable
import com.myrran.domain.entities.mob.spells.effect.EffectFactory
import com.myrran.domain.misc.constants.SpellConstants.Companion.DIRECT_DAMAGE
import com.myrran.domain.skills.created.effect.EffectSkill

class EffectApplierComponent(

    private val effectFactory: EffectFactory

): EffectApplier {

    // EFFECT APPLYING
    //--------------------------------------------------------------------------------------------------------

    override fun applyEffects(caster: Entity, effectSkill: EffectSkill, collision: Collision) {

        applyDirectDamage(effectSkill, collision)
        applyEffect(caster, effectSkill, collision)
    }

    private fun applyDirectDamage(effectSkill: EffectSkill, collision: Collision) {

        if (collision.corporeal is Vulnerable) {

            effectSkill.getStat(DIRECT_DAMAGE)?.let {

                val amount = HP (it.totalBonus().value)
                val damage = Damage(amount, DamageType.FIRE, collision)

                collision.corporeal.receiveDamage(damage)
            }
        }
    }

    private fun applyEffect(caster: Entity, effectSkill: EffectSkill, collision: Collision) {

        if (collision.corporeal is Effectable) {

            val effect = effectFactory.createEffect(caster, effectSkill)
            collision.corporeal.addEffect(effect)
        }
    }
}
