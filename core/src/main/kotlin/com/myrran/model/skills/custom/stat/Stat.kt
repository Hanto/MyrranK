package com.myrran.model.skills.custom.stat

sealed interface Stat
{
    val id: StatId
    val name: StatName
    val baseBonus: StatBonus
    fun totalBonus(): StatBonus
    fun totalCost(): UpgradeCost
}
