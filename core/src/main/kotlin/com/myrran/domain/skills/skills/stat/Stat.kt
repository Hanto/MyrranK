package com.myrran.domain.skills.skills.stat

sealed interface Stat
{
    val id: StatId
    val name: StatName
    val baseBonus: StatBonus
    fun totalBonus(): StatBonus
    fun totalCost(): UpgradeCost
}
