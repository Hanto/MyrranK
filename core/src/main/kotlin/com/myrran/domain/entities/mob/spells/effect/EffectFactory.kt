package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.collisioner.Location
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.mob.spells.effect.stackable.StackableComponent
import com.myrran.domain.misc.constants.SpellConstants.Companion.EXPIRATION
import com.myrran.domain.misc.constants.SpellConstants.Companion.MAX_STACKS
import com.myrran.domain.misc.metrics.time.Second
import com.myrran.domain.misc.metrics.time.Tick
import com.myrran.domain.skills.created.effect.EffectSkill

class EffectFactory
{
    fun createEffect(effectSkill: EffectSkill, caster: Entity, location: Location): Effect =

        when(effectSkill.type) {

            EffectType.DAMAGE -> createDamageEffect(effectSkill, caster, location)
            EffectType.DOT -> createDotEffect(effectSkill, caster, location)
            EffectType.BOMB -> TODO()
            EffectType.SLOW -> createSlowEffect(effectSkill, caster, location)
        }

    private fun createDamageEffect(effectSkill: EffectSkill, caster: Entity, location: Location): DamageEffect =

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
            ),
            location = location
        )

    private fun createDotEffect(effectSkill: EffectSkill, caster: Entity, location: Location): DotEffect =

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
            ),
            location = location
        )


    private fun createSlowEffect(effectSkill: EffectSkill, caster: Entity, location: Location): SlowEffect =

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
            ),
            location = location
        )
}
