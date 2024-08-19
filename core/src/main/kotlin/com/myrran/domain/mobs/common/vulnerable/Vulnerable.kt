package com.myrran.domain.mobs.common.vulnerable

interface Vulnerable {

    fun getHPs(): HP
    fun getMaxHps(): HP

    fun receiveDamage(damage: HP)
    fun receiveHealing(healing: HP)
}
