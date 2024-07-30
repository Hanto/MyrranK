package com.myrran.domain.skills.skills.stat

data class NumUpgrades(

    val value: Int,

) {
    companion object {
        val ZERO = NumUpgrades(0)
    }

    operator fun plus(upgradeBy: NumUpgrades): NumUpgrades =

        NumUpgrades((value + upgradeBy.value))

    operator fun times(bonusPerUpgrade: BonusPerUpgrade): StatBonus =

        StatBonus(value * bonusPerUpgrade.value)

    operator fun times(upgradeCost: UpgradeCost): UpgradeCost =

        UpgradeCost(value * upgradeCost.value)

    fun atMax(max: NumUpgrades): NumUpgrades =

        NumUpgrades(value.coerceAtMost(max.value))

    fun atMin(min: NumUpgrades): NumUpgrades =

        NumUpgrades(value.coerceAtLeast(min.value))
}
