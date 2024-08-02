package com.myrran.domain.skills.custom.stat

data class Stats(

    private val statMap: Map<StatId, Stat>

): StatsI
{
    override fun getStats(): Collection<Stat> = statMap.values

    override fun getStat(statId: StatId): Stat? = statMap[statId]

    override fun upgrade(statId: StatId, upgradeBy: NumUpgrades) =

        when (val stat = statMap[statId]) {
            is StatUpgradeable -> stat.upgradeBy(upgradeBy)
            is StatFixed, null -> Unit
        }

    override fun statCost(): UpgradeCost =

        statMap.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc + next }
}
