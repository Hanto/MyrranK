package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.collisioner.Location
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.common.statuses.Status
import com.myrran.domain.entities.common.statuses.StatusesComponent
import com.myrran.domain.entities.common.vulnerable.Damage
import com.myrran.domain.entities.common.vulnerable.DamageType
import com.myrran.domain.entities.common.vulnerable.HP
import com.myrran.domain.entities.mob.spells.effect.stackable.Stackable
import com.myrran.domain.entities.mob.spells.effect.stackable.StackableComponent
import com.myrran.domain.misc.constants.SpellConstants.Companion.DIRECT_DAMAGE
import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillName

class DamageEffect(

    override val id: EntityId,
    override val caster: Entity,
    private val effectSkill: EffectSkill,
    private val consumable: ConsumableComponent,
    private val stackable: StackableComponent,
    private val location: Location,

): Effect, Consumable by consumable, Stackable by stackable
{
    override val effectType = effectSkill.type
    override val effectSkillId = effectSkill.id
    override val allowToStack = true
    override var statusEffects: MutableList<Status> = mutableListOf()
    override val damages: MutableList<Damage> = mutableListOf()

    override fun effectName(): EffectSkillName = effectSkill.name

    override fun onEffectStarted(statuses: StatusesComponent) {

        val amount = effectSkill.getStat(DIRECT_DAMAGE)!!.totalBonus().value.let { HP(it) }
        val damage = Damage(caster, amount, DamageType.COLD, location)

        damages.add(damage)
    }

    override fun ofEffectTicked(statuses: StatusesComponent) {}

    override fun onEffectEnded(statuses: StatusesComponent) {}
}
