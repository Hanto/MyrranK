package com.myrran.model.skills.custom.stat

data class UpgradeCost(
    val value: Float
) {

    companion object {
        val ZERO = UpgradeCost(0.0f)
    }

    fun sum(upgrade: UpgradeCost): UpgradeCost = UpgradeCost(value + upgrade.value)
}
