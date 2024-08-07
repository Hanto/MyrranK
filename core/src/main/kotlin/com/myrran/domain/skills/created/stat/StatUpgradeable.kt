package com.myrran.domain.skills.created.stat

data class StatUpgradeable(

    override val id: StatId,
    override val name: StatName,
    override val baseBonus: StatBonus,
    val upgrades: Upgrades,
    val upgradeCost: UpgradeCost,
    val bonusPerUpgrade: BonusPerUpgrade

): Stat {

    override fun totalBonus(): StatBonus =

        baseBonus + (upgrades.actual * bonusPerUpgrade)

    override fun totalCost(): UpgradeCost =

        upgrades.actual * upgradeCost

    fun upgradeBy(numUpgrades: NumUpgrades) =

        upgrades.upgradeBy(numUpgrades)
}
