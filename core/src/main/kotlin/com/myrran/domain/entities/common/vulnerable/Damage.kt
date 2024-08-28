package com.myrran.domain.entities.common.vulnerable

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.collisioner.Location

data class Damage(

    val source: Entity,
    val amount: HP,
    val type: DamageType,
    val location: Location
)
