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

    override fun totalBonus(): StatBonus = baseBonus.sum(actual.multiply(bonusPerUpgrade))
    override fun totalCost(): UpgradeCost = actual.multiply(upgradeCost)

    fun upgrade(upgradeBy: Upgrades) {

        actual = actual.sum(upgradeBy)
            .atMin(Upgrades.ZERO)
            .atMax(maximum)
    }
}
