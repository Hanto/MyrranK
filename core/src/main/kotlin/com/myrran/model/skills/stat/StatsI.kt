package com.myrran.model.skills.stat

interface StatsI
{
    fun getStats(): Collection<Stat>
    fun getStat(statId: StatId): Stat?
    fun upgrade(statId: StatId, upgradeBy: Upgrades)
    fun totalCost(): UpgradeCost
}
