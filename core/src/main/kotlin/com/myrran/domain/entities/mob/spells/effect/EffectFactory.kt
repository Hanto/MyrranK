package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.mob.spells.effect.stackable.StackableComponent
import com.myrran.domain.misc.constants.SpellConstants.Companion.EXPIRATION
import com.myrran.domain.misc.constants.SpellConstants.Companion.MAX_STACKS
import com.myrran.domain.misc.metrics.time.Second
import com.myrran.domain.misc.metrics.time.Tick
import com.myrran.domain.skills.created.effect.EffectSkill

class EffectFactory
{
    fun createEffect(caster: Entity, effectSkill: EffectSkill): Effect =

        when(effectSkill.type) {

            EffectType.DAMAGE -> createDamageEffect(caster, effectSkill)
            EffectType.DOT -> createDotEffect(caster, effectSkill)
            EffectType.BOMB -> TODO()
            EffectType.SLOW -> createSlowEffect(caster, effectSkill)
        }

    private fun createDamageEffect(caster: Entity, effectSkill: EffectSkill): DamageEffect =

        DamageEffect(
            id = EntityId(),
            caster = caster,
            effectSkill = effectSkill,
            consumable = ConsumableComponent(
                currentDuration = Second(0),
                maximumDuration = Second(1.5f) ),
            stackable = StackableComponent(
                currentStack = 1,
                maxStacks = effectSkill.getStat(MAX_STACKS)?.totalBonus()?.value?.toInt() ?: 1
            )
        )

    private fun createDotEffect(caster: Entity, effectSkill: EffectSkill): DotEffect =

        DotEffect(
            id = EntityId(),
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


    private fun createSlowEffect(caster: Entity, effectSkill: EffectSkill): SlowEffect =

        SlowEffect(
            id = EntityId(),
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
