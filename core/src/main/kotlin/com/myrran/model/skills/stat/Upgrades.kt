package com.myrran.model.skills.stat

data class Upgrades(
    val value: Int,
) {
    companion object {
        val ZERO = Upgrades(0)
    }

    fun sum(upgradeBy: Upgrades): Upgrades = Upgrades((value + upgradeBy.value))
    fun atMax(max: Upgrades): Upgrades = Upgrades(value.coerceAtMost(max.value))
    fun atMin(min: Upgrades): Upgrades = Upgrades(value.coerceAtLeast(min.value))
    fun multiply(bonusPerUpgrade: BonusPerUpgrade): StatBonus = StatBonus(value * bonusPerUpgrade.value)
    fun multiply(upgradeCost: UpgradeCost): UpgradeCost = UpgradeCost(value * upgradeCost.value)
}
