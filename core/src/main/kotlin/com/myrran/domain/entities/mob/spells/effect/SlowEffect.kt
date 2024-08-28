package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.collisioner.Location
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.common.statuses.Status
import com.myrran.domain.entities.common.statuses.Status.Chilled
import com.myrran.domain.entities.common.statuses.Status.Slowed
import com.myrran.domain.entities.common.statuses.StatusesComponent
import com.myrran.domain.entities.common.vulnerable.Damage
import com.myrran.domain.entities.mob.spells.effect.stackable.Stackable
import com.myrran.domain.entities.mob.spells.effect.stackable.StackableComponent
import com.myrran.domain.misc.constants.SpellConstants.Companion.MAGNITUDE
import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillName

data class SlowEffect(

    override val id: EntityId,
    override val caster: Entity,
    private val effectSkill: EffectSkill,
    private val consumable: ConsumableComponent,
    private val stackable: StackableComponent,
    private val location: Location

): Effect, Consumable by consumable, Stackable by stackable
{
    override val effectType = effectSkill.type
    override val effectSkillId = effectSkill.id
    override val allowToStack = true
    override val statusEffects: MutableList<Status> = mutableListOf()
    override val damages: MutableList<Damage> = mutableListOf()

    override fun effectName(): EffectSkillName = effectSkill.name

    override fun onEffectStarted(statuses: StatusesComponent) {

        val slowPerStack = effectSkill.getStat(MAGNITUDE)!!.totalBonus().value / 100
        val slowPercentage = 1 - (slowPerStack * stackable.numberOfStacks())

        statusEffects.add(Chilled(100))
        statusEffects.add(Slowed(slowPercentage))
    }

    override fun ofEffectTicked(statuses: StatusesComponent) {}

    override fun onEffectEnded(statuses: StatusesComponent) {}
}
