package com.myrran.domain.skills.skills.stat

data class Upgrades(

    var actual: NumUpgrades,
    val maximum: NumUpgrades
) {

    fun upgradeBy(numUpgrades: NumUpgrades) {

        actual += numUpgrades
            .atMin(NumUpgrades.ZERO)
            .atMax(maximum)
    }
}
