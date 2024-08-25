package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.mob.spells.effect.stackable.Stackable
import com.myrran.domain.entities.mob.spells.effect.stackable.StackableComponent
import com.myrran.domain.misc.metrics.time.Second
import com.myrran.domain.skills.created.effect.EffectSkill

class DamageEffect(

    override val casterId: EntityId,
    private val effectSkill: EffectSkill,
    private val consumable: ConsumableComponent,
    private val stackable: StackableComponent

): Effect, Consumable by consumable, Stackable by stackable
{

    override fun tickEffect(entity: Entity) {

    }
}
