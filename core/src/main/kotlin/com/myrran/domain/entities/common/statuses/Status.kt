package com.myrran.domain.entities.common.statuses

sealed interface Status {

    data class Slowed(val magnitude: Float): Status
    data class Chilled(val chillAmount: Int): Status
    data class VulnerableToDamage(val damageFamily: DamageFamily, val percentage: Long): Status
    enum class DamageFamily { COLD, FIRE }
}

