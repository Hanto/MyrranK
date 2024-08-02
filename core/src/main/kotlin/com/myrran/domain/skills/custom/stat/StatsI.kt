package com.myrran.domain.skills.custom.stat

interface StatsI
{
    fun getStats(): Collection<Stat>
    fun getStat(statId: StatId): Stat?
    fun upgrade(statId: StatId, upgradeBy: NumUpgrades)
    fun statCost(): UpgradeCost
}
