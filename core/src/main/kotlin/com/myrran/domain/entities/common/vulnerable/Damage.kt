package com.myrran.domain.entities.common.vulnerable

import com.myrran.domain.entities.common.collisioner.Collision
import com.myrran.domain.entities.common.collisioner.Location

data class Damage(

    val amount: HP,
    val type: DamageType,
    val location: Location
)
