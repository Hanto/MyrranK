package com.myrran.domain.skills.stat

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@type")
sealed interface Stat
{
    val id: StatId
    val name: StatName
    val baseBonus: StatBonus
    fun totalBonus(): StatBonus
    fun totalCost(): UpgradeCost
}
