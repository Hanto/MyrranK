package com.myrran.domain.entities.common.effectapplier

import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.entities.common.effectable.Effectable
import com.myrran.domain.entities.common.vulnerable.Damage
import com.myrran.domain.entities.common.vulnerable.DamageLocation
import com.myrran.domain.entities.common.vulnerable.DamageType
import com.myrran.domain.entities.common.vulnerable.HP
import com.myrran.domain.entities.common.vulnerable.Vulnerable
import com.myrran.domain.misc.constants.SpellConstants.Companion.DIRECT_DAMAGE
import com.myrran.domain.skills.created.effect.EffectSkill

class EffectApplierComponent
{
    fun applyEffects(effectSkill: EffectSkill, target: Corporeal, location: DamageLocation) {

        applyDirectDamage(effectSkill, target, location)
        applyEffect(effectSkill, target, location)
    }

    private fun applyEffect(effectSkill: EffectSkill, target: Corporeal, location: DamageLocation) {

        if (target is Effectable) {

            val effect = effectSkill.type.build(effectSkill)
            target.addEffect(effect)
        }
    }

    private fun applyDirectDamage(effectSkill: EffectSkill, target: Corporeal, location: DamageLocation) {

        if (target is Vulnerable) {

            effectSkill.getStat(DIRECT_DAMAGE)?.let {

                val amount = HP (it.totalBonus().value)
                val damage = Damage(amount, DamageType.FIRE, location)

                target.receiveDamage(damage)
            }
        }
    }
}
