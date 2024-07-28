package com.myrran.domain.skills.skills.stat

data class StatBonus(
    val value: Float
) {

    operator fun plus(statBonus: StatBonus): StatBonus =

        StatBonus(value + statBonus.value)
}
