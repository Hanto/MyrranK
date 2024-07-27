package com.myrran.domain.skills.stat

data class StatBonus(
    val value: Float
) {

    fun sum(statBonus: StatBonus): StatBonus = StatBonus(value + statBonus.value)
}
