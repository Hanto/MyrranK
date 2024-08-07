package com.myrran.domain.skills.created.stat

data class UpgradeCost(

    val value: Float
) {

    companion object {

        val ZERO = UpgradeCost(0.0f)
    }

    operator fun plus(upgrade: UpgradeCost): UpgradeCost =

        UpgradeCost(value + upgrade.value)
}
