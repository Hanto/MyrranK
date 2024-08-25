package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.mob.spells.effect.stackable.StackableComponent
import com.myrran.domain.misc.constants.SpellConstants
import com.myrran.domain.misc.constants.SpellConstants.Companion.MAX_STACKS
import com.myrran.domain.skills.created.effect.EffectSkill

class EffectFactory
{
    fun createEffect(casterId: EntityId, effectSkill: EffectSkill): Effect =

        when(effectSkill.type) {

            EffectType.DAMAGE -> createDamageEffect(casterId, effectSkill)
            EffectType.DOT -> createDotEffect(casterId, effectSkill)
            EffectType.BOMB -> TODO()
        }

    private fun createDamageEffect(casterId: EntityId, effectSkill: EffectSkill): DamageEffect =

        DamageEffect(
            casterId = casterId,
            effectSkill = effectSkill,
            consumable = ConsumableComponent(),
            stackable = StackableComponent(
                currentStack = 1,
                maxStacks = effectSkill.getStat(MAX_STACKS)!!.totalBonus().value.toInt()
            )
        )

    private fun createDotEffect(casterId: EntityId, effectSkill: EffectSkill): DotEffect =

        DotEffect(
            casterId = casterId,
            effectSkill = effectSkill,
            consumable = ConsumableComponent(),
            stackable = StackableComponent(
                currentStack = 1,
                maxStacks = effectSkill.getStat(MAX_STACKS)!!.totalBonus().value.toInt()
            )
        )
}
