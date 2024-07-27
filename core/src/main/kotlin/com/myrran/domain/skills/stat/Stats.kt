package com.myrran.domain.skills.stat

data class Stats(

    private val statMap: Map<StatId, Stat>

): StatsI
{
    override fun getStats(): Collection<Stat> = statMap.values

    override fun getStat(statId: StatId): Stat? = statMap[statId]

    override fun upgrade(statId: StatId, upgradeBy: Upgrades) =

        when (val stat = statMap[statId]) {
            is StatUpgradeable -> stat.upgrade(upgradeBy)
            is StatFixed, null -> Unit
        }

    override fun totalCost(): UpgradeCost =

        statMap.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.sum(next) }
}
