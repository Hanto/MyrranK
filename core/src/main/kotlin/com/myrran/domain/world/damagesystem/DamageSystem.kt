package com.myrran.domain.world.damagesystem

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.steerable.Steerable
import com.myrran.domain.entities.common.vulnerable.DamageLocation
import com.myrran.domain.entities.common.vulnerable.Vulnerable
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
                eventDispatcher.sendEvent(EntityHPsReducedEvent(entity.id, it.amount))

                if (entity is Steerable && it.location is DamageLocation.Location) {

                    entity.applyImpulse(it.location.direction, 100f)
                }

            }
            entity.clearAllDamage()
        }
    }
}
