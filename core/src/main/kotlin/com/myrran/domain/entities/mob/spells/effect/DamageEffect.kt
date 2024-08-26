package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.mob.spells.effect.stackable.Stackable
import com.myrran.domain.entities.mob.spells.effect.stackable.StackableComponent
import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillName

class DamageEffect(

    override val id: EntityId,
    override val caster: Entity,
    private val effectSkill: EffectSkill,
    private val consumable: ConsumableComponent,
    private val stackable: StackableComponent

): Effect, Consumable by consumable, Stackable by stackable
{
    override val effectType = effectSkill.type
    override fun effectName(): EffectSkillName = effectSkill.name
    override fun effectStarted(entity: Entity) {}
    override fun effectTicked(entity: Entity) {}
    override fun effectEnded(entity: Entity) {}
}
