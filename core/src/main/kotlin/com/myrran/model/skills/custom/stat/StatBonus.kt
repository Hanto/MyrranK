package com.myrran.model.skills.custom.stat

data class StatBonus(
    val value: Float
) {

    fun sum(statBonus: StatBonus): StatBonus = StatBonus(value + statBonus.value)
}
