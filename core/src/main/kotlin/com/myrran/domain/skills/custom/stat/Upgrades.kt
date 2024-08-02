package com.myrran.domain.skills.custom.stat

import com.myrran.domain.skills.custom.stat.NumUpgrades.Companion.ZERO

data class Upgrades(

    var actual: NumUpgrades,
    val maximum: NumUpgrades
) {

    fun upgradeBy(numUpgrades: NumUpgrades) {

        actual = (numUpgrades + actual)
            .atMin(ZERO)
            .atMax(maximum)
    }
}
