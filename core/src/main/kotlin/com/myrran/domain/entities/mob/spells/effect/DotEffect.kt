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
import com.myrran.domain.misc.constants.SpellConstants
import com.myrran.domain.misc.constants.SpellConstants.Companion.DAMAGE_PER_TICK
import com.myrran.domain.misc.constants.SpellConstants.Companion.EXPIRATION
import com.myrran.domain.misc.constants.SpellConstants.Companion.MAX_STACKS
import com.myrran.domain.misc.constants.SpellConstants.Companion.SPEED
import com.myrran.domain.misc.metrics.time.Second
import com.myrran.domain.misc.metrics.time.Tick
import com.myrran.domain.skills.created.effect.EffectSkill

class DotEffect(

    override val casterId: EntityId,
    private val effectSkill: EffectSkill,
    private val consumable: ConsumableComponent

): Effect, Consumable by consumable
{
    private var currentStacks: Int = 1;

    fun currentStacks(): Int =

        currentStacks

    fun maxStacks(): Int =

        effectSkill.getStat(MAX_STACKS)!!.totalBonus().value.toInt()

    override fun tickEffect(entity: Entity) {

        if (entity is Vulnerable) {

            val amount = effectSkill.getStat(DAMAGE_PER_TICK)!!.totalBonus().value.let { HP(it) }
            val damage = Damage(amount, DamageType.FIRE, NoLocation)

            entity.receiveDamage(damage)
        }
    }
}

