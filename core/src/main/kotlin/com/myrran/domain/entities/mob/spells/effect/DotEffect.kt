package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.collisioner.NoLocation
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.common.vulnerable.Damage
import com.myrran.domain.entities.common.vulnerable.DamageType
import com.myrran.domain.entities.common.vulnerable.HP
import com.myrran.domain.entities.common.vulnerable.Vulnerable
import com.myrran.domain.entities.mob.spells.effect.stackable.Stackable
import com.myrran.domain.entities.mob.spells.effect.stackable.StackableComponent
import com.myrran.domain.misc.constants.SpellConstants.Companion.DAMAGE_PER_TICK
import com.myrran.domain.misc.metrics.time.Second
import com.myrran.domain.skills.created.effect.EffectSkill

data class DotEffect(

    override val caster: Entity,
    private val effectSkill: EffectSkill,
    private val consumable: ConsumableComponent,
    private val stackable: StackableComponent

): Effect, Consumable by consumable, Stackable by stackable
{
    override val effectType = effectSkill.type

    override fun effectStarted(entity: Entity) {}

    override fun effectTicked(entity: Entity) {

        if (entity is Vulnerable) {

            val amount = effectSkill.getStat(DAMAGE_PER_TICK)!!.totalBonus().value
                .let { HP(it * numberOfStacks()) }

            val damage = Damage(amount, DamageType.FIRE, NoLocation)

            entity.receiveDamage(damage)
        }
    }

    override fun effectEnded(entity: Entity) {}

    override fun update(deltaTime: Second) {

        consumable.updateDuration(deltaTime)
    }
}
