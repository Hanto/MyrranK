package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.mob.spells.effect.stackable.StackableComponent
import com.myrran.domain.misc.constants.SpellConstants
import com.myrran.domain.misc.constants.SpellConstants.Companion.EXPIRATION
import com.myrran.domain.misc.constants.SpellConstants.Companion.MAX_STACKS
import com.myrran.domain.misc.metrics.time.Tick
import com.myrran.domain.skills.created.effect.EffectSkill

class EffectFactory
{
    fun createEffect(caster: Entity, effectSkill: EffectSkill): Effect =

        when(effectSkill.type) {

            EffectType.DAMAGE -> createDamageEffect(caster, effectSkill)
            EffectType.DOT -> createDotEffect(caster, effectSkill)
            EffectType.BOMB -> TODO()
        }

    private fun createDamageEffect(caster: Entity, effectSkill: EffectSkill): DamageEffect =

        DamageEffect(
            caster = caster,
            effectSkill = effectSkill,
            consumable = ConsumableComponent(),
            stackable = StackableComponent(
                currentStack = 1,
                maxStacks = effectSkill.getStat(MAX_STACKS)?.totalBonus()?.value?.toInt() ?: 1
            )
        )

    private fun createDotEffect(caster: Entity, effectSkill: EffectSkill): DotEffect =

        DotEffect(
            caster = caster,
            effectSkill = effectSkill,
            consumable = ConsumableComponent(
                currentDuration = Tick(0),
                maximumDuration = effectSkill.getStat(EXPIRATION)!!.totalBonus().value.let { Tick(it) } ),
            stackable = StackableComponent(
                currentStack = 1,
                maxStacks = effectSkill.getStat(MAX_STACKS)!!.totalBonus().value.toInt()
            )
        )
}
