package com.myrran.domain.skills.custom.stat

data class NumUpgrades(

    val value: Int,

) {
    companion object {

        val ZERO = NumUpgrades(0)
    }

    operator fun plus(other: NumUpgrades): NumUpgrades =

        NumUpgrades((value + other.value))

    operator fun minus(other: NumUpgrades): NumUpgrades =

        NumUpgrades(value - other.value)

    operator fun times(bonusPerUpgrade: BonusPerUpgrade): StatBonus =

        StatBonus(value * bonusPerUpgrade.value)

    operator fun times(upgradeCost: UpgradeCost): UpgradeCost =

        UpgradeCost(value * upgradeCost.value)

    operator fun compareTo(other: NumUpgrades): Int =

        value.compareTo(other.value)

    fun atMax(max: NumUpgrades): NumUpgrades =

        NumUpgrades(value.coerceAtMost(max.value))

    fun atMin(min: NumUpgrades): NumUpgrades =

        NumUpgrades(value.coerceAtLeast(min.value))
}
