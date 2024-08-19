package com.myrran.domain.mobs.common.vulnerable

interface Vulnerable {

    fun getHPs(): HP
    fun getMaxHps(): HP

    fun increaseHps(hps: HP)
    fun reduceHps(hps: HP)

    fun receiveDamage(damage: Damage)
    fun retrieveDamage(): List<Damage>
    fun clearAllDamage()
}
