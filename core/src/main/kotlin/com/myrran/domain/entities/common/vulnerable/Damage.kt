package com.myrran.domain.entities.common.vulnerable

import com.myrran.domain.entities.common.collisioner.Collision

data class Damage(

    val amount: HP,
    val type: DamageType,
    val location: Collision
)
