package com.myrran.domain.skills.skills.stat

data class StatUpgradeable(

    override val id: StatId,
    override val name: StatName,
    override val baseBonus: StatBonus,
    var actual: Upgrades,
    val maximum: Upgrades,
    val upgradeCost: UpgradeCost,
    val bonusPerUpgrade: BonusPerUpgrade

): Stat {

    override fun totalBonus(): StatBonus =

        baseBonus + (actual * bonusPerUpgrade)

    override fun totalCost(): UpgradeCost =

        actual * upgradeCost

    fun upgrade(upgradeBy: Upgrades) {

        actual += upgradeBy
            .atMin(Upgrades.ZERO)
            .atMax(maximum)
    }
}
