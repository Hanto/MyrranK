package com.myrran.domain.world.damagesystem

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.collisioner.ExactLocation
import com.myrran.domain.entities.common.steerable.Steerable
import com.myrran.domain.entities.common.vulnerable.Damage
import com.myrran.domain.entities.common.vulnerable.Vulnerable
import com.myrran.domain.entities.mob.player.Player
import com.myrran.domain.events.EntityHPsReducedEvent
import com.myrran.infraestructure.eventbus.EventDispatcher

class DamageSystem(

    private val eventDispatcher: EventDispatcher
)
{

    fun applyDamages(entity: Entity) {

        if (entity is Vulnerable) {

            val damage = entity.retrieveDamage()
            damage.forEach { applyDamage(entity, it) }
            entity.clearAllDamage()
        }
    }

    private fun<VulnerableEntity> applyDamage(target: VulnerableEntity, damage: Damage) where VulnerableEntity: Entity, VulnerableEntity: Vulnerable {

        target.reduceHps( damage.amount )

        eventDispatcher.sendEvent(EntityHPsReducedEvent(target.id, damage))

        if (target is Steerable && target !is Player && damage.location is ExactLocation) {

            target.applyImpulse(damage.location.direction, 400f)
        }
    }
}
