package com.myrran.domain.skills.skills.stat

interface StatsI
{
    fun getStats(): Collection<Stat>
    fun getStat(statId: StatId): Stat?
    fun upgrade(statId: StatId, upgradeBy: Upgrades)
    fun statCost(): UpgradeCost
}
