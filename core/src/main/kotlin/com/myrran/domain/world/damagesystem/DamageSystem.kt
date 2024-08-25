package com.myrran.domain.world.damagesystem

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.collisioner.Collision
import com.myrran.domain.entities.common.steerable.Steerable
import com.myrran.domain.entities.common.vulnerable.Vulnerable
import com.myrran.domain.entities.mob.player.Player
import com.myrran.domain.events.EntityHPsReducedEvent
import com.myrran.infraestructure.eventbus.EventDispatcher

class DamageSystem(

    private val eventDispatcher: EventDispatcher
)
{

    fun applyDamage(entity: Entity) {

        if (entity is Vulnerable) {

            val damage = entity.retrieveDamage()
            damage.forEach {

                entity.reduceHps( it.amount )
                eventDispatcher.sendEvent(EntityHPsReducedEvent(entity.id, it))

                if (entity is Steerable && entity !is Player && it.location is Collision) {

                    entity.applyImpulse(it.location.direction, 400f)
                }
            }
            entity.clearAllDamage()
        }
    }
}
