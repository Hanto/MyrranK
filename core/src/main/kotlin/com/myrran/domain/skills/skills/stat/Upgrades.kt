package com.myrran.domain.skills.skills.stat

data class Upgrades(

    val value: Int,

) {
    companion object {
        val ZERO = Upgrades(0)
    }

    operator fun plus(upgradeBy: Upgrades): Upgrades =

        Upgrades((value + upgradeBy.value))

    operator fun times(bonusPerUpgrade: BonusPerUpgrade): StatBonus =

        StatBonus(value * bonusPerUpgrade.value)

    operator fun times(upgradeCost: UpgradeCost): UpgradeCost =

        UpgradeCost(value * upgradeCost.value)

    fun atMax(max: Upgrades): Upgrades =

        Upgrades(value.coerceAtMost(max.value))

    fun atMin(min: Upgrades): Upgrades =

        Upgrades(value.coerceAtLeast(min.value))
}
