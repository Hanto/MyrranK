package com.myrran.model.skills.custom.stat

data class StatFixed(

    override val id: StatId,
    override val name: StatName,
    override val baseBonus: StatBonus

): Stat {

    override fun totalBonus(): StatBonus = baseBonus
    override fun totalCost(): UpgradeCost = UpgradeCost(0.0f)
}
