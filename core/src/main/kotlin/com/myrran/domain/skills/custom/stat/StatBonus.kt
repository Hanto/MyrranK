package com.myrran.domain.skills.custom.stat

data class StatBonus(
    val value: Float
) {

    operator fun plus(statBonus: StatBonus): StatBonus =

        StatBonus(value + statBonus.value)
}
