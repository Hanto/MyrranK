package com.myrran.domain.world.damagesystem

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.vulnerable.Vulnerable

class DamageSystem {

    fun applyDamage(entity: Entity) {

        if (entity is Vulnerable) {

            val damage = entity.retrieveDamage()
            damage.forEach { entity.reduceHps( it.amount ); println("${entity::class} receives: ${it.amount}")  }
            entity.clearAllDamage()
        }
    }
}
